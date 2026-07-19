import { useEffect, useState } from "react";

const API = "/api/items";

export default function App() {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState({ name: "", description: "" });
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState("");

  const load = () => {
    fetch(API)
      .then((r) => {
        if (!r.ok) throw new Error("Failed to load items");
        return r.json();
      })
      .then(setItems)
      .catch((e) => setError(e.message));
  };

  useEffect(() => {
    load();
  }, []);

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    const url = editingId ? `${API}/${editingId}` : API;
    const method = editingId ? "PUT" : "POST";
    try {
      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (!res.ok) throw new Error("Save failed");
      setForm({ name: "", description: "" });
      setEditingId(null);
      load();
    } catch (e) {
      setError(e.message);
    }
  };

  const edit = (item) => {
    setForm({ name: item.name, description: item.description });
    setEditingId(item.id);
  };

  const cancelEdit = () => {
    setForm({ name: "", description: "" });
    setEditingId(null);
  };

  const remove = async (id) => {
    try {
      const res = await fetch(`${API}/${id}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Delete failed");
      load();
    } catch (e) {
      setError(e.message);
    }
  };

  return (
    <div className="container">
      <h2>Items</h2>

      <form onSubmit={submit}>
        <input
          placeholder="Name"
          value={form.name}
          onChange={(e) => setForm({ ...form, name: e.target.value })}
          required
        />
        <input
          placeholder="Description"
          value={form.description}
          onChange={(e) => setForm({ ...form, description: e.target.value })}
        />
        <button type="submit">{editingId ? "Update" : "Add"}</button>
        {editingId && (
          <button type="button" onClick={cancelEdit}>
            Cancel
          </button>
        )}
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {items.length === 0 ? (
        <p className="empty">No items yet. Add one above.</p>
      ) : (
        <ul>
          {items.map((i) => (
            <li key={i.id}>
              <div>
                <b>{i.name}</b>
                {i.description ? ` — ${i.description}` : ""}
              </div>
              <div className="actions">
                <button onClick={() => edit(i)}>Edit</button>
                <button className="delete" onClick={() => remove(i.id)}>
                  Delete
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
