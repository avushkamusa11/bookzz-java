import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Controller, useForm } from "react-hook-form";
import ReactSelect from "react-select";
import "../../styles/add-forms.css";

export default function AddBookComponent() {
  const [title, setTitle] = useState(null);
  const [isbn, setIsbn] = useState("");
  const [authors, setAuthors] = useState([]);
  const [pages, setPages] = useState("");
  const [description, setDescription] = useState("");
  const [file, setFile] = useState(null);
  const [book, setBook] = useState("");
  const [genres, setGenres] = useState([]);
  const [pdf, setPdf] = useState(null);

  const navigate = useNavigate();

  const changeTitleHandler = (event) => {
    setTitle(event.target.value);
  };
  const changeIsbnHandler = (event) => {
    setIsbn(event.target.value);
  };
  const changeFileHandler = (event) => {
    setFile(event.target.files[0]);
  };

  const changePagesHandler = (event) => {
    setPages(event.target.value);
  };
  const changeDescriptionHandler = (event) => {
    setDescription(event.target.value);
  };
  const changePdfHandler = (event) => {
    setPdf(event.target.files[0]);
  };

  async function saveBook(data) {
    console.log("Description: " + description);
    console.log(JSON.stringify(data.selectedGenres));

    const body = {
      title: title,
      isbn: isbn,
      pages: pages,
      description: description,
      genre: data.selectedGenres.id,
      authors: data.selectedAuthors,
    };

    const response = await fetch(`http://localhost:8081/book/save`, {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });
    const fetchedItems = await response.json(response);
    setBook(fetchedItems);
  }
  useEffect(() => {
    const handleSubmitImage = async () => {
      if (file && book.title) {
        const formData = new FormData();
        formData.append("image", file);

        try {
          const response = await fetch(
            `http://localhost:8081/file/uploadBook/${book.id}`,
            {
              method: "POST",
              credentials: "include",
              body: formData,
            }
          );
        } catch (error) {
          console.error("Error uploading image:", error);
        }
      }
    };

    handleSubmitImage();
  }, [book]);

  useEffect(() => {
    const handleSubmitPdf = async () => {
      if (pdf && book.title) {
        const formData = new FormData();
        formData.append("pdf", pdf.name);

        try {
          const response = await fetch(
            `http://localhost:8081/file/uploadBookpdf/${book.id}`,
            {
              method: "POST",
              credentials: "include",
              body: formData,
            }
          );
        } catch (error) {
          console.error("Error uploading pdf:", error);
        }
      }
    };

    handleSubmitPdf();
  }, [book]);
  useEffect(() => {
    async function fetchAuthors() {
      const response = await fetch(`http://localhost:8081/author/all`);
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setAuthors(fetchedItems);
      console.log(JSON.stringify(authors));
    }

    fetchAuthors();
  }, []);
  useEffect(() => {
    async function fetchGenres() {
      const response = await fetch(`http://localhost:8081/genre/all`);
      const fetchedItems = await response.json(response);
      //console.log(JSON.stringify(fetchedItems));
      setGenres(fetchedItems);
    }
    fetchGenres();
  }, []);
  useEffect(() => {
    if (book && book.title) {
      navigate(`/books`);
    }
  }, [book]);
  const { control, handleSubmit } = useForm();

  return (
    <div>
      <Form
        className="add-form"
        onSubmit={handleSubmit((data) => {
          if (data) {
            saveBook(data);
          }
        })}
        autoComplete="off"
      >
        <h2>Add Book</h2>
        <Form.Group className="box" controlId="formGroupUsername">
          <Form.Label className="label">Title</Form.Label>
          <Form.Control
            className="input"
            id="usernameTextField"
            type="text"
            placeholder="Enter title"
            value={title}
            onChange={changeTitleHandler}
          />
        </Form.Group>
        <Form.Group className="box" controlId="formGroupPassword">
          <Form.Label className="label" id="passwordLabel">
            ISBN
          </Form.Label>
          <Form.Control
            className="input"
            id="passwordTextField"
            type="text"
            placeholder="Enter ISBN"
            value={isbn}
            onChange={changeIsbnHandler}
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Authors</Form.Label>
          <Controller
            className="col"
            name="selectedAuthors"
            control={control}
            render={({ field }) => (
              <ReactSelect
                {...field}
                isMulti
                options={authors}
                getOptionLabel={(option) => option.fName}
                getOptionValue={(option) => option.fName}
                isLoading={!authors}
                placeholder="Authors"
              />
            )}
          />
        </Form.Group>

        <Form.Group>
          <Form.Label>Genres</Form.Label>
          <Controller
            className="col"
            name="selectedGenres"
            control={control}
            render={({ field }) => (
              <ReactSelect
                {...field}
                options={genres}
                getOptionLabel={(option) => option.genreName}
                getOptionValue={(option) => option.genreName}
                isLoading={!genres}
                placeholder="Genres"
              />
            )}
          />
        </Form.Group>
        <Form.Group className="box" controlId="formGroupFirstName">
          <Form.Label className="label">Pages</Form.Label>
          <Form.Control
            className="input"
            id="usernameTextField"
            type="number"
            placeholder="Enter pages"
            value={pages}
            onChange={changePagesHandler}
          />
        </Form.Group>
        <Form.Group controlId="formFile" className="mb-3">
          <Form.Label>Add image</Form.Label>
          <Form.Control type="file" onChange={changeFileHandler} />
        </Form.Group>
        <Form.Group controlId="formFile" className="mb-3">
          <Form.Label>Add PDF</Form.Label>
          <Form.Control type="file" onChange={changePdfHandler} />
        </Form.Group>
        <Form.Group className="box" controlId="formGroupLastName">
          <Form.Label className="label">Description</Form.Label>
          <textarea
            className="input"
            id="usernameTextField"
            type="text"
            placeholder="Enter Description"
            value={description}
            style={{ width: "100%" }}
            onChange={changeDescriptionHandler}
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
