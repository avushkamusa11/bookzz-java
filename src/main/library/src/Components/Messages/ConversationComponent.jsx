import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "../../styles/messages.css";

export default function ConversationComponent() {
  const { id } = useParams();
  const loggedUser = JSON.parse(localStorage.getItem("user"));
  const [conversation, setConveration] = useState([]);
  const [user, setUser] = useState("");
  const [messageText, setMessageText] = useState("");
  const changeMessageHandlar = (event) => {
    setMessageText(event.target.value);
  };
  useEffect(() => {
    async function fetchUser() {
      const response = await fetch(`http://localhost:8081/user/get/${id}`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setUser(fetchedItems);
    }
    fetchUser();
  }, []);
  useEffect(() => {
    async function fetchConveration() {
      const response = await fetch(
        `http://localhost:8081/conversation/getOneConversation/${id}`,
        { credentials: "include" }
      );
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setConveration(fetchedItems);
    }
    fetchConveration();
  }, []);
  async function sendMessage() {
    const body = {
      reciever: id,
      message: messageText,
    };

    const response = await fetch(`http://localhost:8081/conversation/add`, {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });
  }
  return (
    <div>
      <div>
        <img
          src={"/images/" + user.profilePicture}
          style={{ width: "50px", height: "50px", borderRadius: "100%" }}
        ></img>
        <h2>
          {user.fName} {user.lName}
        </h2>
        <div>
          {conversation.map((conv) => (
            <div key={conv.id}>
              {conv.sender.id === loggedUser.id ? (
                <div style={{ width: "100%" }}>
                  <div
                    className=""
                    style={{
                      display: "flex",
                      justifyContent: "flex-end",
                      textAlign: "right",
                      paddingTop: "2%",
                      width: "100%",
                    }}
                  >
                    <div className="inner-div-message">
                      <h5>{conv.message}</h5>
                      <div> {conv.sendTime}</div>
                    </div>
                  </div>
                </div>
              ) : (
                <div>
                  <div className="" style={{ paddingTop: "2%" }}>
                    <div className="inner-div-message">
                      <h5>{conv.message}</h5>
                      <div> {conv.sendTime}</div>
                    </div>
                  </div>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
      <Form className="form" onSubmit={sendMessage} autoComplete="off">
        <div className="messages-form">
          <Form.Group className="box">
            <Form.Control
              className="input"
              as="textarea"
              rows={1}
              value={messageText}
              onChange={changeMessageHandlar}
            />
          </Form.Group>

          <Button
            id="btnSubmit"
            type="submit"
            style={{
              backgroundColor: " #FFF3E1",
              border: "none",
              width: "40px",
            }}
          >
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <img
              src="/images/send-icon-png-2.jpg"
              style={{ width: "20px" }}
            ></img>
          </Button>
        </div>
      </Form>
    </div>
  );
}
