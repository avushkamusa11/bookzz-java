import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Controller, useForm } from "react-hook-form";
import ReactSelect from "react-select";
import useSWR from "swr";

export default function UpdateBookComponent() {
  const { id } = useParams();
  const [book, setBook] = useState("");
  const [title, setTitle] = useState("");
  const [isbn, setIsbn] = useState("");
  const [authors, setAuthors] = useState([]);
  const [pages, setPages] = useState("");
  const [description, setDescription] = useState("");

  console.log(JSON.stringify(book));
  const [updatedBook, setUpdatedBook] = useState({});
  const [genres, setGenres] = useState([]);
  const [file, setFile] = useState(null);
  const [pdf, setPdf] = useState(null);
  const navigate = useNavigate();
  useEffect(() => {
    if (book) {
      setTitle(book.title);
      setIsbn(book.isbn);
      setDescription(book.description);
      setPages(book.pages);
    }
  }, [book.title]);
  const changeTitleHandler = (event) => {
    setTitle(event.target.value);
  };
  const changeIsbnHandler = (event) => {
    setIsbn(event.target.value);
  };
  const changePagesHandler = (event) => {
    setPages(event.target.value);
  };
  const changeDescriptionHandler = (event) => {
    setDescription(event.target.value);
  };
  const changeFileHandler = (event) => {
    setFile(event.target.files[0]);
  };
  const changePdfHandler = (event) => {
    setPdf(event.target.files[0]);
  };
  async function saveBook(data) {
    const body = {
      title: title,
      isbn: isbn,
      pages: pages,
      description: description,
      genre: data.selectedGenres.id,
      authors: data.selectedAuthors,
    };
    if (data) {
      const response = await fetch(`http://localhost:8081/book/update/${id}`, {
        method: "PUT",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setUpdatedBook(fetchedItems);
    }
  }
  useEffect(() => {
    const handleSubmitImage = async () => {
      if (file && updatedBook.title) {
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
  }, [updatedBook.title]);
  useEffect(() => {
    async function fetchBook() {
      const response = await fetch(`http://localhost:8081/book/${id}`);
      const fetchedItems = await response.json(response);
      setBook(fetchedItems);
    }
    fetchBook();
    if (book) {
      setTitle(book.title);
      setIsbn(book.isbn);
      setDescription(book.description);
      setPages(book.pages);
    }
  }, []);
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
      //console.log(JSON.stringify(fetchedItems));
      setAuthors(fetchedItems);
    }

    fetchAuthors();
  }, []);
  useEffect(() => {
    async function fetchGenres() {
      const response = await fetch(`http://localhost:8081/genre/all`);
      const fetchedItems = await response.json(response);
      setGenres(fetchedItems);
    }

    fetchGenres();
  }, []);

  const { control, handleSubmit } = useForm();
  if (book.title) {
    return (
      <div>
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
            <h2>Update Book</h2>
            <Form.Group className="box" controlId="formGroupUsername">
              <Form.Label className="label">Title</Form.Label>
              <Form.Control
                className="input"
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
                type="text"
                placeholder="Enter Description"
                style={{ width: "100%" }}
                value={description}
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
      </div>
    );
  }
}
