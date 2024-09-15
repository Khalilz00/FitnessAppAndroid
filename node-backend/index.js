
const express = require('express')
const app = express()
const PORT = 3000

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.get('/get-sessions', (req, res) => {
    res.send('name id blabla!')
})

app.listen(PORT, () => {
  console.log(`Example app listening on port ${PORT}`)
})
