import { openDB } from "./db";

export async function initDB() {
  const db = await openDB()

  await db.get("PRAGMA foreign_keys = ON")

  await db.run('CREATE TABLE IF NOT EXISTS package (id INTEGER PRIMARY KEY, flight_ticket_id INTEGER, hotel_room_id TEXT, event_ticket_id INTEGER, user_id INTEGER, event_name TEXT, flight_description TEXT, room_type TEXT)');
}