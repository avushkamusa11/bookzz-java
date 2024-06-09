import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "../../styles/book-style.css";
import { FaStar } from "react-icons/fa";

export default function BookComponent() {
  const { id } = useParams();
  const [book, setBook] = useState("");
  const [comments, setComments] = useState([]);
  const [comment, setComment] = useState("");
  const [addedComment, setAddedComment] = useState("");
  const [pageState, setPageState] = useState(false);
  const [isAdded, setIsAdded] = useState(false);
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));
  const isAdmin = user && user.role && user.role.roleName === "admin";
  const isAuth = user && user.id;

  const changeCommentHandler = (event) => {
    setComment(event.target.value);
  };

  useEffect(() => {
    async function fetchBook() {
      const response = await fetch(`http://localhost:8081/book/${id}`);
      const fetchedItems = await response.json(response);

      setBook(fetchedItems);
    }
    fetchBook();
  }, []);

  useEffect(() => {
    async function fetchComments() {
      const response = await fetch(`http://localhost:8081/comments/${id}`);
      const fetchedItems = await response.json(response);

      setComments(fetchedItems);
    }
    fetchComments();
  }, [addedComment]);

  async function saveComment(e) {
    e.preventDefault();
    const response = await fetch(`http://localhost:8081/comments/${id}`, {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(comment),
    });
    const fetchedItems = await response.json(response);
    setAddedComment(fetchedItems);
  }

  async function saveBookInMyLibrary() {
    const status = "To Read";
    const response = await fetch(
      `http://localhost:8081/myLibrary/${id}/${status}`,
      {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(),
      }
    );
    setIsAdded(!isAdded);
  }

  async function checkBook() {
    const response = await fetch(
      `http://localhost:8081/myLibrary/checkBook/${id}`,
      {
        method: "GET",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(),
      }
    );
    const fetchedItems = await response.json(response);
    setIsAdded(fetchedItems);
    setPageState(!pageState);
  }
  const hasPdf = book.bookPdf !== null;
  if (book && book.authors && book.genres) {
    return (
      <div className="background">
        <div className="book-section">
          <div className="line-book"></div>
          <div className="book-img">
            {" "}
            <img src={"/images/" + book.file} className="img-content"></img>
          </div>
          {hasPdf ? (
            <Button
              variant="dark"
              className="read"
              onClick={() => navigate(`/read/${book.id}`)}
            >
              <div class="button__line"></div>
              <div class="button__line"></div>
              <span class="button__text">READ</span>
              <div class="button__drow1"></div>
              <div class="button__drow2"></div>
            </Button>
          ) : (
            <div></div>
          )}

          <div className="book-content">
            <h1>{book.title}</h1>
            {[...Array(5)].map((_, index) => {
              const ratingValue = index + 1;
              return (
                <FaStar
                  className="star"
                  color={ratingValue <= book.rate ? "#ffc107" : "#e4e5e9"}
                  size={20}
                />
              );
            })}
            <div>{book.rate.toFixed(2)}</div>
            <h4>
              {book.authors.map((author) => (
                <div key={author.id}>
                  <div>Author: {author.fName}</div>
                </div>
              ))}
            </h4>
            <h4>
              {book.genres.map((genre) => (
                <div key={genre.id}>
                  <div>Genre: {genre.genreName}</div>
                </div>
              ))}
            </h4>
            <h4>ISBN: {book.isbn}</h4>
            <h4>Pages: {book.pages}</h4>
            <p style={{ fontFamily: "cursive" }}>
              Description: {book.description}
            </p>

            {isAuth ? (
              <div className="btn-show-more">
                {!pageState ? (
                  <Button className="main-buttons" onClick={checkBook}>
                    Show more
                  </Button>
                ) : (
                  <div></div>
                )}
              </div>
            ) : (
              <div> </div>
            )}
          </div>
          {pageState ? (
            <div>
              <div className="comment-section">
                {isAdded ? (
                  <div></div>
                ) : (
                  <Button
                    className="main-buttons btn-add"
                    id="loginBtn"
                    variant="dark"
                    type="submit"
                    onClick={saveBookInMyLibrary}
                  >
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    Add to my library
                  </Button>
                )}
                {comments.map((comment) => (
                  <div key={comment.id}>
                    <div className="column-comment">
                      <img
                        className="profile-picture-comments"
                        src={"/images/" + comment.user.profilePicture}
                      ></img>
                      <h4>{comment.user.username}</h4>
                    </div>
                    <div className="column-comment">
                      <h4>{comment.comment}</h4>
                    </div>
                  </div>
                ))}

                <Form
                  className="form"
                  onSubmit={saveComment}
                  autoComplete="off"
                >
                  <Form.Group className="box" controlId="formGroupUsername">
                    <Form.Label className="label">Add Comment</Form.Label>
                    <Form.Control
                      className="input"
                      type="text"
                      placeholder="Enter comment"
                      value={comment}
                      onChange={changeCommentHandler}
                      style={{ width: "90%" }}
                    />
                  </Form.Group>
                  <Button
                    className="main-buttons"
                    id="loginBtn"
                    variant="dark"
                    type="submit"
                  >
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    Add comment
                  </Button>
                </Form>
              </div>
            </div>
          ) : (
            <div></div>
          )}
        </div>
      </div>
    );
  }
}
