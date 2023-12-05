export async function checkUserAuth(req, res, next) {
  try {
    if (!req.headers['user-id']) {
      throw new Error('Usuário não está autenticado')
    }
    next()
  } catch (e) {
    return res.status(500).json({ message: String(e) })
  }
}