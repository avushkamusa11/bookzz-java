import { useEffect } from "react";
import { useState } from "react";
import { Document, Page, pdfjs } from "react-pdf";
import { useParams } from "react-router-dom";

export default function ReadBookComponent() {
  const { id } = useParams();
  const [book, setBook] = useState("");

  pdfjs.GlobalWorkerOptions.workerSrc = `https://cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;
  useEffect(() => {
    async function fetchBook() {
      const response = await fetch(`http://localhost:8081/book/${id}`);
      const fetchedItems = await response.json(response);

      setBook(fetchedItems);
    }
    fetchBook();
  }, []);
  const pdfUrl = "/pdf/" + book.bookPdf;
  const [numPages, setNumPages] = useState(0); // To be determined after loading the PDF

  const onDocumentLoadSuccess = ({ numPages }) => {
    setNumPages(numPages);
  };
  console.log(JSON.stringify(numPages));

  return (
    <div style={{ width: "100%", height: "1000px" }}>
      <iframe src={"/pdf/" + book.bookPdf} width="100%" height="100%" />
    </div>
  );
}
