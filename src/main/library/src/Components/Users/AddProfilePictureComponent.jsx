import { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function AddProfilePictureComponent(refresh, onReRender) {
  const [selectedFile, setSelectedFile] = useState(null);
  const [user, setUser] = useState(JSON.parse(localStorage.getItem("user")));
  const navigate = useNavigate();
  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (selectedFile) {
      const formData = new FormData();
      formData.append("image", selectedFile);

      try {
        const response = await fetch(
          `http://localhost:8081/file/uploadProfile/${user.id}`,
          {
            method: "POST",
            credentials: "include",
            body: formData,
          }
        );
        const fetchedItems = await response.json(response);
        setUser(fetchedItems);

        // Handle the response or perform additional actions
      } catch (error) {
        console.error("Error uploading image:", error);
      }
    }
    localStorage.setItem("user", JSON.stringify(user));
    refresh = () => onReRender();
    console.log(JSON.stringify(user));
    navigate("/MyProfile");
  };

  return (
    <div>
      <Form className="form" onSubmit={handleSubmit} autoComplete="off">
        <h2>Add Profile picture</h2>
        <Form.Group controlId="formFile" className="mb-3">
          <Form.Label>Default file input example</Form.Label>
          <Form.Control type="file" onChange={handleFileChange} />
        </Form.Group>

        <Button className="button" id="btnSubmit" variant="dark" type="submit">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          Submit
        </Button>
      </Form>
    </div>
  );
}
