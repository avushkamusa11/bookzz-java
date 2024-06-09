import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useForm, Controller } from "react-hook-form";
import {
  Col,
  Row,
  Container,
  InputGroup,
  OverlayTrigger,
  Tooltip,
  FormGroup,
} from "react-bootstrap";
import ReactDatePicker from "react-datepicker";
import "../../styles/add-forms.css";

export default function AddAuthorComponent() {
  const [name, setName] = useState("");
  const [author, setAuthor] = useState("");
  const navigate = useNavigate();

  const changeNameHandler = (event) => {
    setName(event.target.value);
  };

  async function saveAuthor(data) {
    const response = await fetch(
      `http://localhost:8081/author/${name}/${data.dob}`,
      {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(),
      }
    );
    const fetchedItems = await response.json(response);
    console.log(JSON.stringify(fetchedItems));
    setAuthor(fetchedItems);
  }

  useEffect(() => {
    if (author) {
      navigate(`/authors`);
    }
  }, [author]);
  const { register, control, handleSubmit } = useForm();
  return (
    <div>
      <Form
        className="add-form authors"
        onSubmit={handleSubmit((data) => {
          if (data) {
            saveAuthor(data);
          }
        })}
        autoComplete="off"
      >
        <h2>Add Author</h2>
        <Form.Group className="box" controlId="formGroupUsername">
          <Form.Label className="label">Name</Form.Label>
          <Form.Control
            className="input"
            id="authorNameTextField"
            type="text"
            placeholder="Enter title"
            value={name}
            onChange={changeNameHandler}
          />
        </Form.Group>
        <FormGroup>
          <Form.Label></Form.Label>
          <Controller
            control={control}
            name="dob"
            render={({ field: { onChange, onBlur, value, ref } }) => (
              <InputGroup className="datepicker-group">
                <InputGroup.Text id="addon-from">Date of birth</InputGroup.Text>
                <ReactDatePicker
                  className="form-control datepicker"
                  onChange={onChange}
                  onBlur={onBlur}
                  selected={value}
                  dateFormat="dd/MM/yyyy"
                  style={{ backgroundColor: "rgb(255, 255, 255)" }}
                  aria-describedby="addon-from"
                />
              </InputGroup>
            )}
          />
        </FormGroup>
        <Button className="main-buttons" id="btnSubmit" type="submit">
          <span>SAVE</span>
          <i></i>
        </Button>
      </Form>
    </div>
  );
}
