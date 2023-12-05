import { openDB } from "../database/db"

export async function getAllPackages(req, res) {
  try {
    const userId = req.headers['user-id']

    const db = await openDB()

    const packages = await db.all('select * from package where user_id = ?', userId)

    return res.json(packages)
  } catch (e) {
    return res.status(500).json({ message: String(e) })
  }
}

export async function getPackage(req, res) {
  try {
    const { packageId } = req.params

    const userId = req.headers['user-id']

    const db = await openDB()

    const packages = await db.all('select * from package where user_id = ? and id = ?', [userId, packageId])

    return res.json(packages)
  } catch (e) {
    return res.status(500).json({ message: String(e) })
  }
}

export async function addPackage(req, res) {
  try {
    const { flightTicketId, flightTrajectory, hotelRoomId, roomType, eventTicketId, eventName } = req.body

    const userId = req.headers['user-id']

    const db = await openDB()

    const packages = await db.run('insert into package (flight_ticket_id, hotel_room_id, event_ticket_id, user_id, event_name, flight_description, room_type) values(?, ?, ?, ?, ?, ?, ?)', [flightTicketId, hotelRoomId, eventTicketId, userId, eventName, flightTrajectory, roomType])

    return res.json(packages)
  } catch (e) {
    return res.status(500).json({ message: String(e) })
  }
}

export async function deletePackage(req, res) {
  try {
    const { packageId } = req.params

    const db = await openDB()

    const packages = await db.run('delete from package where id = ?', packageId)

    return res.json(packages)
  } catch (e) {
    return res.status(500).json({ message: String(e) })
  }
}