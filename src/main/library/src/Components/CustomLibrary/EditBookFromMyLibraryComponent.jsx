import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Controller, useForm } from "react-hook-form";
import ReactSelect from "react-select";

import { FaStar } from "react-icons/fa";

export default function EditBookFromMyLibraryComponent() {
  const { id } = useParams();
  const [book, setBook] = useState({});
  const [rating, setRating] = useState(0);
  const [isAdded, setIsAdded] = useState(false);

  const handleRatingChange = (value) => {
    setRating(value);
  };
  const status = [
    { label: "Read", value: "Read" },
    { label: "In progres", value: "In progres" },
    { label: "In Wish List", value: "InWish List" },
  ];
  const navigate = useNavigate();
  console.log(id);
  useEffect(() => {
    async function fetchBook() {
      const response = await fetch(
        `http://localhost:8081/myLibrary/get/${id}`,
        { credentials: "include" }
      );
      const fetchedItems = await response.json(response);

      setBook(fetchedItems);
    }
    fetchBook();
  }, [id]);

  async function saveMyBook(data) {
    if (data) {
      await fetch(
        `http://localhost:8081/myLibrary/${id}/${data.selectState.value}`,
        {
          method: "PUT",
          credentials: "include",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(),
        }
      );
    }
  }

  async function addRate() {
    await fetch(`http://localhost:8081/book/rate/${id}/${rating}`, {
      method: "PUT",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(),
    });
    navigate("/myLibrary");
  }
  console.log(JSON.stringify(book));
  const isRead = book.status === "Read";
  const { control, handleSubmit } = useForm();
  if (book.book) {
    return (
      <div>
        <Form
          className="form"
          onSubmit={handleSubmit((data) => {
            saveMyBook(data);
            addRate();
          })}
        >
          <h2>{book.book.title}</h2>
          <img src={"/images/" + book.book.file} width="20%" height="30%" />
          <Form.Group>
            <Controller
              className="col"
              name="selectState"
              control={control}
              render={({ field }) =>
                isRead ? (
                  <ReactSelect
                    {...field}
                    options={status}
                    getOptionLabel={(option) => option.label}
                    getOptionValue={(option) => option.value}
                    isLoading={!status}
                    placeholder="Status"
                    isDisabled
                  />
                ) : (
                  <ReactSelect
                    {...field}
                    options={status}
                    getOptionLabel={(option) => option.label}
                    getOptionValue={(option) => option.value}
                    isLoading={!status}
                    placeholder="Status"
                  />
                )
              }
            />
          </Form.Group>
          <Form.Group>
            <div>
              {[...Array(5)].map((_, index) => {
                const ratingValue = index + 1;
                return (
                  <label key={index}>
                    <input
                      type="radio"
                      name="rating"
                      value={ratingValue}
                      onClick={() => handleRatingChange(ratingValue)}
                    />
                    <FaStar
                      className="star"
                      color={ratingValue <= rating ? "#ffc107" : "#e4e5e9"}
                      size={20}
                    />
                  </label>
                );
              })}
              <p>Selected rating: {String(rating)}</p>
            </div>
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
            SAVE
          </Button>
        </Form>
      </div>
    );
  }
}
