import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

export default function EditProfileOptionComponent() {
  const navigate = useNavigate();
  return (
    <div>
      <Button className="tab" onClick={() => navigate("/profilePicture")}>
        Add/Change profile picture
      </Button>
      <Button className="tab" onClick={() => navigate("/biographyUpdate")}>
        Add biography
      </Button>
    </div>
  );
}
