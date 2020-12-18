import wsStore from "./websockets.js";
import { writable } from 'svelte/store';

// TODO : Remove me, this is for testing
const cards = [{
  id: 1,
  title: "Title",
  body: "Enter your text here",
}]

const store = writable(undefined);

let socket;
wsStore.subscribe((ws) => {
  socket = ws;
  socket.onmessage = (e) => {
    const message = JSON.parse(e.data);
    console.debug("new message : ", message);

    switch (message.event) {
      // Room related event
      case "enter_room":
        store.set({
          id: message.room, cards // TODO : modify backend, message.cards
        });
        break;
      case "error_entering_room":
        console.log("TODO, make a popup or something appear");
        break;

      // Cards related event
      case "new_card":
        console.log("TODO, new card");
        break;
      case "deleted_card":
        console.log("TODO, new card");
        break;

      case "error_modifying_card":
        console.log("TODO, resync client cards");
        break;
    }
  }
});

function createRoom() {
  console.debug("create_room", socket.readyState);
  if (socket.readyState)
    socket.send(JSON.stringify({ event: "create_room" }));
}

function joinRoom(id) {
  console.debug("join_room", socket.readyState);
  if (socket.readyState)
    socket.send(JSON.stringify({ event: "join_room", id }));
  else socket.onopen = () =>
    socket.send(JSON.stringify({ event: "join_room", id }));
}

function leaveRoom() {
  console.debug("leave_room", socket.readyState);
  if (socket.readyState) {
    socket.send(JSON.stringify({ event: "leave_room" }));
    store.set(undefined);
  }
}

function newCard() {
  console.debug("new_card", socket.readyState);
  if (socket.readyState)
    socket.send(JSON.stringify({ event: "new_card" }));
}

function updateCard(card) {
  console.debug("update_card", socket.readyState);
  if (socket.readyState)
    socket.send(JSON.stringify({ event: "update_card", card }));
}

function deleteCard(id) {
  console.debug("delete_card", socket.readyState);
  if (socket.readyState)
    socket.send(JSON.stringify({ event: "delete_card", id }));
}

export default {
  subscribe: store.subscribe,
  set: store.set,
  create: createRoom,
  join: joinRoom,
  leave: leaveRoom,
  cards: {
    add: newCard,
    update: updateCard,
    delete: deleteCard,
  }
}