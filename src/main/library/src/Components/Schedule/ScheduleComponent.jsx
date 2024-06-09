import { useState } from "react";
import { Table } from "react-bootstrap";
import Stack from "@mui/material/Stack";
import TextField from "@mui/material/TextField";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import ReactSelect from "react-select";
import { InputGroup, FormGroup } from "react-bootstrap";
import ReactDatePicker from "react-datepicker";
import { useForm, Controller, get } from "react-hook-form";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import { blue, blueGrey, green, purple } from "@mui/material/colors";
import "react-datepicker/dist/react-datepicker.css";

import { StaticTimePicker } from "@mui/x-date-pickers-pro";
import "../../styles/time-picker.css";
import { useEffect } from "react";
import Popup from "reactjs-popup";
import "reactjs-popup/dist/index.css";
import SubNavComponent from "../SubNavComponent";
export default function ScheduleComponent() {
  const [books, setBooks] = useState([]);
  const [addedPlan, setAddedPlan] = useState("");
  const [palns, setPlans] = useState([]);
  const [startTime, setStartTime] = useState(new Date());
  const handleChangeStartTime = (newValue) => {
    setStartTime(newValue);
  };
  const [endTime, setEndTime] = useState(new Date());
  const handleChangeEndTime = (newValue) => {
    setEndTime(newValue);
  };
  const date = new Date();
  const currentYear = date.getFullYear();
  const currentMonth = date.getMonth() + 1;

  const daysInCurrentMonth = getDaysInMonth(currentYear, currentMonth);
  function getDaysInMonth(year, month) {
    return new Date(year, month, 0).getDate();
  }
  useEffect(() => {
    async function fetchBooks() {
      const response = await fetch(`http://localhost:8081/myLibrary/myBooks`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setBooks(fetchedItems);
      console.log(JSON.stringify(fetchedItems));
    }
    fetchBooks();
  }, []);
  useEffect(() => {
    async function fetchPlans() {
      const response = await fetch(`http://localhost:8081/schedule/getPlans`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setPlans(fetchedItems);
      console.log(JSON.stringify(fetchedItems));
    }
    fetchPlans();
  }, [addedPlan]);
  const hours = [
    "6:00",
    "7:00",
    "8:00",
    "9:00",
    "10:00",
    "11:00",
    "12:00",
    "13:00",
    "14:00",
    "15:00",
    "16:00",
    "17:00",
    "18:00",
    "19:00",
    "20:00",
    "21:00",
    "22:00",
    "23:00",
    "00:00",
  ];
  const theme = createTheme({
    typography: {
      fontSize: 12,
      fontFamily: "Segoe UI, Helvetica, Arial, sans-serif",
    },
    body: {
      backgroundColor: "#DAFFFB",
    },
    palette: {
      background: {
        default: "#DAFFFB",
      },
    },
  });
  async function addPlan(data) {
    const response = await fetch(
      `http://localhost:8081/schedule/addPlan/${data.date}/${startTime}/${endTime}/${data.selectedBook.book.id}`,
      {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(),
      }
    );
    const fetchedItems = await response.json(response);
    setAddedPlan(fetchedItems);
  }
  const { register, control, handleSubmit } = useForm();

  const hasPlan = (plan, time, day) => {
    const temp = time.split(":");
    const temp2 = plan.statTime.split(":");
    const tempDate = new Date(plan.date).getDate();
    console.log(tempDate);
    if (temp[0] === temp2[0] && tempDate === day) {
      return true;
    }
    return false;
  };
  return (
    <div>
      <SubNavComponent />
      <Form
        onSubmit={handleSubmit((data) => {
          addPlan(data);
          if (data) {
          }
        })}
        autoComplete="off"
      >
        <ThemeProvider theme={theme}>
          <div style={{ margin: "5%" }}>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <Stack spacing={0}>
                <div className="grid-container">
                  <div className="grid-container date">
                    <FormGroup className="date">
                      <Controller
                        control={control}
                        name="date"
                        render={({
                          field: { onChange, onBlur, value, ref },
                        }) => (
                          <InputGroup className="datepicker-group">
                            <InputGroup.Text id="addon-from">
                              Date
                            </InputGroup.Text>
                            <ReactDatePicker
                              className="form-control datepicker"
                              onChange={onChange}
                              onBlur={onBlur}
                              selected={value}
                              dateFormat="dd/MM/yyyy"
                              style={{
                                backgroundColor: "rgb(255, 255, 255)",
                              }}
                              aria-describedby="addon-from"
                            />
                          </InputGroup>
                        )}
                      />
                    </FormGroup>

                    <Form.Group className="date">
                      <Controller
                        className="col"
                        name="selectedBook"
                        control={control}
                        render={({ field }) => (
                          <ReactSelect
                            {...field}
                            options={books}
                            getOptionLabel={(option) => option.book.title}
                            getOptionValue={(option) => option.book.title}
                            isLoading={!books}
                            placeholder="Books"
                          />
                        )}
                      />
                    </Form.Group>
                  </div>

                  <div className="grid-child startTime">
                    <StaticTimePicker
                      className="clock"
                      label="Start Time"
                      value={startTime}
                      onChange={handleChangeStartTime}
                      renderInput={(params) => <TextField {...params} />}
                    />{" "}
                  </div>
                  <div className="grid-child endTime">
                    <StaticTimePicker
                      className="clock"
                      label="End Time"
                      value={endTime}
                      onChange={handleChangeEndTime}
                      renderInput={(params) => <TextField {...params} />}
                    />
                  </div>
                </div>
              </Stack>
            </LocalizationProvider>
          </div>
        </ThemeProvider>
        <Button className="main-buttons" type="submit">
          ADD
        </Button>
      </Form>

      <Table responsive bordered>
        <thead>
          <tr bordered>
            <th>#</th>
            {hours.map((hour) => (
              <th key={hour} bordered style={{ border: "solid 2px" }}>
                {hour}
              </th>
            ))}
          </tr>
        </thead>
        <tbody bordered>
          {Array.from({ length: daysInCurrentMonth }).map((_, index) => (
            <tr key={index} bordered style={{ border: "solid 2px" }}>
              <td>{index + 1}</td>
              {palns.map((plan) =>
                hours.map((hour) => (
                  <td key={hour}>
                    {hasPlan(plan, hour, index + 1) ? (
                      <Popup
                        trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                        position="right center"
                      >
                        <div>
                          {plan.statTime} {plan.endTime} {plan.book.title}
                        </div>
                      </Popup>
                    ) : (
                      <div></div>
                    )}
                  </td>
                ))
              )}
              {/*  {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "07:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "08:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "09:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "10:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "11:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "12:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "13:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "14:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "15:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "16:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "17:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "18:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "19:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "20:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "21:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "22:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "23:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))}
              {palns.map((plan) => (
                <td key={plan.id}>
                  {hasPlan(plan, "00:00", index + 1) ? (
                    <Popup
                      trigger={<div style={{ textAlign: "center" }}>✔️</div>}
                      position="right center"
                    >
                      <div>
                        {plan.statTime} {plan.endTime} {plan.book.title}
                      </div>
                    </Popup>
                  ) : (
                    <div></div>
                  )}
                </td>
              ))} */}
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}
