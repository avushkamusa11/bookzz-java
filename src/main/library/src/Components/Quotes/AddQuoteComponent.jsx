import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import ReactSelect from "react-select";
import { Controller, useForm } from "react-hook-form";
import "../../styles/add-forms.css";

export default function AddQuoteComponent() {
  const [books, setBooks] = useState([]);
  const [quoteText, setQuoteText] = useState("");
  const [quote, setQuote] = useState("");

  const navigate = useNavigate();

  const changeQuoteHandler = (event) => {
    setQuoteText(event.target.value);
  };

  useEffect(() => {
    async function fetchBooks() {
      const response = await fetch(`http://localhost:8081/myLibrary/myBooks`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setBooks(fetchedItems);
      console.log(JSON.stringify(fetchedItems));
    }
    fetchBooks();
  }, []);
  async function saveQuote(data) {
    console.log(JSON.stringify(data.selectedBook.book.id));
    console.log(quoteText);
    if (data) {
      const response = await fetch(
        `http://localhost:8081/quotes/${data.selectedBook.book.id}`,
        {
          method: "POST",
          credentials: "include",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(quoteText),
        }
      );

      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setQuote(fetchedItems);
    }
  }

  useEffect(() => {
    if (quote) {
      navigate(`/quotes`);
    }
  }, [quote]);
  const { control, handleSubmit } = useForm();
  if (books) {
    return (
      <div>
        <Form
          className="add-form authors"
          onSubmit={handleSubmit((data) => {
            if (data) {
              saveQuote(data);
            }
          })}
          autoComplete="off"
        >
          <h2>Add Quote</h2>
          <Form.Group className="box">
            <Form.Label className="label">Quote text</Form.Label>
            <textarea
              className="input"
              id="usernameTextField"
              type="text"
              placeholder="Add Quote"
              value={quoteText}
              style={{ width: "100%" }}
              onChange={changeQuoteHandler}
            />
          </Form.Group>

          <Form.Group>
            <Form.Label>Select book</Form.Label>
            <Controller
              className="col"
              name="selectedBook"
              control={control}
              render={({ field }) => (
                <ReactSelect
                  {...field}
                  options={books}
                  getOptionLabel={(option) => option.book.title}
                  getOptionValue={(option) => option.book.title}
                  isLoading={!books}
                  placeholder="Books"
                />
              )}
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
            SAVE
          </Button>
        </Form>
      </div>
    );
  }
}
