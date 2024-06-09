import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Controller, useForm } from "react-hook-form";
import ReactSelect from "react-select";
import "../../styles/filter.css";
export default function FilterComponent({
  authors,
  genres,
  books,
  setSelectedAuthor,
  setSelectedGenre,
  setSelectedTitle,
}) {
  const { control, handleSubmit } = useForm();
  return (
    <div>
      <Form
        onSubmit={handleSubmit((data) => {
          if (data) {
            setSelectedAuthor(data.selectedAuthor.fName);
            setSelectedTitle(data.selectedBook.title);
            setSelectedGenre(data.selectedGenre.genreName);
          }
        })}
      >
        <div className="col">
          <Form.Label>Search By Author</Form.Label>
          <Controller
            name="selectedAuthor"
            control={control}
            render={({ field }) => (
              <ReactSelect
                {...field}
                className="filter"
                options={authors}
                getOptionLabel={(option) => option.fName}
                getOptionValue={(option) => option.fName}
                isLoading={!authors}
                placeholder="Authors"
              />
            )}
          />
          <Form.Label>Search By Title</Form.Label>
          <Controller
            name="selectedBook"
            control={control}
            render={({ field }) => (
              <ReactSelect
                {...field}
                className="filter"
                options={books}
                getOptionLabel={(option) => option.title}
                getOptionValue={(option) => option.title}
                isLoading={!books}
                placeholder="Books"
              />
            )}
          />
          <Form.Label>Search By Genre</Form.Label>
          <Controller
            name="selectedGenre"
            control={control}
            render={({ field }) => (
              <ReactSelect
                {...field}
                className="filter"
                options={genres}
                getOptionLabel={(option) => option.genreName}
                getOptionValue={(option) => option.genreName}
                isLoading={!genres}
                placeholder="Genres"
              />
            )}
          />
          <Button className="main-buttons">FILTER</Button>
        </div>
      </Form>
    </div>
  );
}
