import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Col, Row, Button, Container, InputGroup } from "react-bootstrap";
import { useForm, Controller } from "react-hook-form";
import ReactDatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import SubNavComponent from "../SubNavComponent";
import "../../styles/stats.css";

export default function StatisticComponent() {
  const [to, setDateTo] = useState(new Date());
  const [from, setDateFrom] = useState(new Date(to.getFullYear(), 0, 1));
  const [stats, setStats] = useState(0);
  const [state, setState] = useState(false);

  const { register, control, handleSubmit } = useForm({
    defaultValues: { to, from, stats },
  });

  useEffect(() => {
    async function fetchStatsWithFilter() {
      {
        const response = await fetch(
          `http://localhost:8081/statistic/${from}/${to}`,
          { credentials: "include" }
        );
        const fetchedItems = await response.json(response);
        setStats(fetchedItems);
      }
    }
    fetchStatsWithFilter();
    console.log(JSON.stringify(stats));
  }, [state]);

  return (
    <div>
      <SubNavComponent />
      <div className="filter-form-wrapper" style={{ marginTop: "5%" }}>
        <Form
          onSubmit={handleSubmit((data) => {
            setDateTo(new Date(data.to));
            setDateFrom(new Date(data.from));
            setState(!state);
          })}
        >
          <Container>
            <Row md={12}>
              <Col md={1} style={{ textAlign: "right" }}>
                <b>Period:</b>
              </Col>
              <Col md={2}>
                <Controller
                  control={control}
                  name="from"
                  render={({ field: { onChange, onBlur, value, ref } }) => (
                    <Row>
                      <InputGroup className="datepicker-group">
                        <InputGroup.Text id="addon-from">From</InputGroup.Text>
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
                    </Row>
                  )}
                />
              </Col>
              <Col md={2}>
                <Controller
                  control={control}
                  name="to"
                  render={({ field: { onChange, onBlur, value, ref } }) => (
                    <Row>
                      <InputGroup className="datepicker-group">
                        <InputGroup.Text id="addon-to">To</InputGroup.Text>
                        <ReactDatePicker
                          className="form-control datepicker"
                          onChange={onChange}
                          onBlur={onBlur}
                          selected={value}
                          dateFormat="dd/MM/yyyy"
                          style={{ backgroundColor: "rgb(255, 255, 255)" }}
                          aria-describedby="addon-to"
                        />
                      </InputGroup>
                    </Row>
                  )}
                />
              </Col>

              <Col md={1}>
                <Button
                  type="submit"
                  variant="secondary"
                  className="main-buttons"
                >
                  Filter
                </Button>
              </Col>
            </Row>
          </Container>
        </Form>
        <hr className="horizontal-line"></hr>
        <img
          className="stats-img"
          src="/images/photo-1532012197267-da84d127e765.jpg"
        ></img>
      </div>
      <h1 style={{ marginTop: "10%" }}>
        𝓨𝑜𝓊 𝓇𝑒𝒶𝒹 {stats} 𝓅𝒶𝑔𝑒𝓈 𝒻𝓇𝑜𝓂 {from.getDate()}/{from.getMonth() + 1}/
        {from.getFullYear()} 𝓉𝑜
        {to.getDate()}/{to.getMonth() + 1}/{to.getFullYear()}
      </h1>
    </div>
  );
}
