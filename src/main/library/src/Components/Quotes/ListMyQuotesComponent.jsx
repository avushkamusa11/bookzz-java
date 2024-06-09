import { Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function ListMyQuotesComponent() {
  const [quotes, setQuotes] = useState([]);
  const [view, setView] = useState("all");
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchMyQuotes() {
      if (view === "all") {
        const response = await fetch(`http://localhost:8081/quotes/all`);
        const fetchedItems = await response.json(response);
        console.log(JSON.stringify(fetchedItems));
        setQuotes(fetchedItems);
      }
      if (view === "myQuotes") {
        const response = await fetch(`http://localhost:8081/quotes/myQuotes`, {
          credentials: "include",
        });
        const fetchedItems = await response.json(response);
        console.log(JSON.stringify(fetchedItems));
        setQuotes(fetchedItems);
      }
    }
    fetchMyQuotes();
  }, [view]);

  return (
    <div>
      <Button className="tab" onClick={() => setView("all")}>
        All Quotes
      </Button>
      <Button className="tab" onClick={() => setView("myQuotes")}>
        My Quotes
      </Button>
      <Button className="tab" onClick={() => setView("byBooks")}>
        Quotes ByBook
      </Button>
      <div className="row">
        <table id="genre-table" className="table">
          <thead>
            <tr>
              <th className="th-first">Quote</th>
              <th>User</th>
              <th className="th-second">Book</th>
            </tr>
          </thead>
          <tbody>
            {quotes.map((quote) => (
              <tr key={quote.id}>
                <td className="table-row-items" style={{ width: "80%" }}>
                  <h4>{quote.quote}</h4>
                </td>
                <td className="table-row-items">
                  <img
                    src={"/images/" + quote.user.profilePicture}
                    style={{
                      width: "70px",
                      height: "70px",
                      borderRadius: "100%",
                    }}
                  ></img>
                  <h5>{quote.user.username}</h5>
                </td>
                <td className="table-row-items">
                  <h4>{quote.book.title}</h4>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
