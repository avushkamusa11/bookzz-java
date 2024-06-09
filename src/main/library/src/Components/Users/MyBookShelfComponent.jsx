import { useEffect } from "react";
import React from "react";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useState } from "react";
import { useParams } from "react-router-dom";

export default function MyBookShelfComponent(count) {
  const { id } = useParams();
  const [myBooks, setMyBooks] = useState([]);
  useEffect(() => {
    async function fetchBooks() {
      const response = await fetch(
        `http://localhost:8081/myLibrary/books/${id}`,
        {
          credentials: "include",
        }
      );
      const fetchedItems = await response.json(response);
      setMyBooks(fetchedItems);
    }
    fetchBooks();
  }, []);

  const greaterThan3 = count > 3;
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
  };

  return (
    <div>
      <Slider {...settings}>
        {myBooks ? (
          myBooks.map((book) => (
            <div key={book.id}>
              <img src={"/images/" + book.book.file} width="150px"></img>
            </div>
          ))
        ) : (
          <div></div>
        )}
      </Slider>
    </div>
  );
}
