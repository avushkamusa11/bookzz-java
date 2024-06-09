import { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import SubNavComponent from "../SubNavComponent";
import "../../styles/goal-style.css";

export default function AddGoalComponent() {
  const [year, setYear] = useState(new Date().getFullYear());
  const [goal, setGoal] = useState("");
  const [books, setBooks] = useState([]);
  const [countOfBooks, setCountOfBooks] = useState(0);

  const changeCountHandler = (event) => {
    setCountOfBooks(event.target.value);
  };
  useEffect(() => {
    async function fetchGoal() {
      const response = await fetch(
        `http://localhost:8081/goal/myGoal/${year}`,
        { credentials: "include" }
      );
      const fetchedItems = await response.json(response);
      setGoal(fetchedItems);
    }
    fetchGoal();
  }, []);

  useEffect(() => {
    async function fetchBooks() {
      const response = await fetch(
        `http://localhost:8081/myLibrary/readBooks/${year}`,
        { credentials: "include" }
      );
      const fetchedItems = await response.json(response);
      setBooks(fetchedItems);
    }
    fetchBooks();
  }, []);

  async function saveGoal(e) {
    e.preventDefault();
    if (countOfBooks) {
      const response = await fetch(
        `http://localhost:8081/goal/${countOfBooks}`,
        {
          method: "POST",
          credentials: "include",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(),
        }
      );
    }
  }

  if (goal.count) {
    return (
      <div>
        <h1 className="goal-text">
          𝓨𝓸𝓾𝓻 𝓰𝓸𝓪𝓵 𝓽𝓱𝓲𝓼 𝔂𝓮𝓪𝓻 ({goal.year}) : {goal.count} 𝓫𝓸𝓸𝓴𝓼
        </h1>

        <div className="line"></div>
        <img
          className="goal-img"
          src="/images/photo-1532012197267-da84d127e765.jpg"
        ></img>
        <div className="books-in-year">{goal.year} 𝒾𝓃 𝒷𝑜𝑜𝓀𝓈</div>
        <div className="yesr-in-books-img">
          {books.map((book) => (
            <img
              key={book.id}
              src={"/images/" + book.book.file}
              style={{ width: "25%", padding: "2%" }}
            ></img>
          ))}
        </div>
      </div>
    );
  } else {
    return (
      <div>
        <SubNavComponent />
        <Form className="form" onSubmit={saveGoal}>
          <h2>Add Goal for current Year</h2>
          <Form.Group className="box" controlId="formGroupUsername">
            <Form.Label className="label">Count of books</Form.Label>
            <Form.Control
              className="input"
              id="usernameTextField"
              type="text"
              placeholder="Enter count of books you wont to read this year"
              value={countOfBooks}
              onChange={changeCountHandler}
            />
          </Form.Group>
          <Button
            className="main-buttons"
            id="btnSubmit"
            variant="dark"
            type="submit"
          >
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            ADD
          </Button>
        </Form>
      </div>
    );
  }
}
