import { Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function ListGenreComponent() {
  const [genres, setGenres] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    async function fetchGenre() {
      const response = await fetch(`http://localhost:8081/genre/all`);
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setGenres(fetchedItems);
    }
    fetchGenre();
  }, []);

  async function deleteBook(id) {
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
      <table id="genre-table" className="table">
        <thead>
          <tr>
            <th className="th-first">GenreName</th>
            <th className="th-second">Action</th>
          </tr>
        </thead>
        <tbody>
          {genres.map((genre) => (
            <tr key={genre.id}>
              <td className="table-row-items">
                <h4>{genre.genreName}</h4>
              </td>
              <td>
                <Button
                  onClick={() => deleteBook(genre.id)}
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
