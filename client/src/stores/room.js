import wsStore from "./websockets.js";
import { writable } from 'svelte/store';

const store = writable(undefined);
let socket;
wsStore.subscribe((ws) => {
  socket = ws;
  socket.onmessage = (e) => {
    const message = JSON.parse(e.data);
    console.debug("new message : ", message);
    switch (message.event) {
      case "enter_room":
        store.set(message.room);
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

export default {
  subscribe: store.subscribe,
  create: createRoom,
  join: joinRoom,
  leave: leaveRoom,
}