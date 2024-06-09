import { useEffect, useState } from "react";
import "../styles/home-style.css";
export default function HomeComponent() {
  const [books, setBooks] = useState([]);
  useEffect(() => {
    async function fetchBook() {
      const response = await fetch(`http://localhost:8081/book/fantasy`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setBooks(fetchedItems);
      console.log(JSON.stringify(fetchedItems));
    }
    fetchBook();
  }, []);
  return (
    <div>
      <div>
        <div className="home-line line-1"></div>
        <img
          className="images-home img-1"
          src="/images/name-platform-9-3-4-icon-hogwarts-express-vector-45022325.jpg"
        ></img>
        <div className="home-line line-2"></div>
        <img
          className="images-home img-2"
          src="/images/golden-snitch-from-harry-potter-3d-model-9ca5df94b6.jpg"
        ></img>
        <div className="home-line line-3"></div>
        <img
          className="images-home img-3"
          src="/images/1000_F_565252461_lTEcXsgTFeqMbvNAcrgJ5VkKtYvmxZWY.jpg"
        ></img>
        <div className="home-line line-4"></div>
        <img
          className="images-home img-4"
          src="/images/360_F_566910944_TkjQK59m4hSd5VuBQAtkwUvoxfsJ9mx3.jpg"
        ></img>
        <div className="home-line line-5"></div>
        <img
          className="images-home img-5"
          src="/images/c4pzltdlext21.jpg"
        ></img>
      </div>
      <div>
        <img
          className="img-h-express"
          src="/images/on-the-hogwarts-express-adrian-macho.jpg"
        ></img>
        <h2 className="text-home">
          𝙏𝙝𝙚 𝙧𝙞𝙜𝙝𝙩 𝙗𝙤𝙤𝙠 𝙞𝙣 𝙩𝙝𝙚 𝙧𝙞𝙜𝙝𝙩 𝙝𝙖𝙣𝙙𝙨 𝙖𝙩 𝙩𝙝𝙚 𝙧𝙞𝙜𝙝𝙩 𝙩𝙞𝙢𝙚 𝙘𝙖𝙣 𝙘𝙝𝙖𝙣𝙜𝙚 𝙩𝙝𝙚
          𝙬𝙤𝙧𝙡𝙙.
        </h2>
      </div>
      <div className="home-content">
        <h2>𝕸𝖔𝖓𝖙𝖍 𝖔𝖋 𝖙𝖍𝖊 𝖜𝖎𝖟𝖆𝖗𝖉𝖎𝖓𝖌 𝖜𝖔𝖗𝖑𝖉</h2>
        <div>
          {books ? (
            books.map((book) => (
              <img
                style={{ width: "200px", height: "300px", padding: "2%" }}
                src={"/images/" + book.file}
              ></img>
            ))
          ) : (
            <div></div>
          )}
        </div>
      </div>
    </div>
  );
}
