import { Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import dayjs from "dayjs";

export default function ListAuthorsComponent() {
  const [authors, setAuthors] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    async function fetchAuthors() {
      const response = await fetch(`http://localhost:8081/author/all`);
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setAuthors(fetchedItems);
    }
    fetchAuthors();
  }, []);

  async function deleteAuthor(id) {
    /* const response = await fetch(`http://localhost:8081/book/${id}`,
          {
              method: 'DELETE',
              credentials: 'include',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(),
          });
       */
  }

  return (
    <div className="row">
      <table id="authors-table" className="table">
        <thead>
          <tr>
            <th className="th-first">Name</th>
            <th>Date of Birth</th>
            <th className="th-second">Action</th>
          </tr>
        </thead>
        <tbody>
          {authors.map((author) => (
            <tr key={author.id}>
              <td className="table-row-items">
                <h4>{author.fName}</h4>
              </td>
              <td className="table-row-items">
                <h4>{dayjs(author.dateOfBirth).format("DD/MM/YYYY")}</h4>
              </td>
              <td>
                <Button
                  onClick={() => deleteAuthor(author.id)}
                  className="main-buttons"
                >
                  DELETE
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
