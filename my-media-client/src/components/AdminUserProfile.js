import { Link } from "react-router-dom";

export default function AdminUserProfile() {
  return (
    <div>
      <p>admin users will be able to manage the website here</p>
      <Link to="/admin/users" className="btn btn-red">
        Manage Users
      </Link>
    </div>
  );
}
