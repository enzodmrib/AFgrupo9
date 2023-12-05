import { flightsApi } from "../libs/axios"

export async function login(req, res) {
  try {
    const { email, password } = req.body

    const user: any = await flightsApi.post('/login', {
      email,
      password
    })

    if (user) {
      const { id, username, email } = user.data.user

      return res.status(200).json({
        user: {
          id,
          username,
          email
        }
      })
    }
  } catch (e) {
    return res.status(500).json({ message: String(e) })
  }
}