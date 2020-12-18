import socket from "./websockets.js";
import { writable } from 'svelte/store';

const store = writable(undefined);

socket.onmessage = (e) => {
  const message = JSON.parse(e.data);
  switch (message.event) {
    case "rooms":
      console.debug("all rooms : ", message.rooms);
      break;
    case "enter_room":
      console.debug("room", message.room);
      store.set(message.room);
      break;
  }
}

function createRoom() {
  console.debug("createRoom");
  try {
    socket.send(JSON.stringify({ event: "create_room" }));
  } catch (error) {
    console.error("Websocket error...", error.toString());
  }
}

function joinRoom(id) {
  console.debug("joinRoom", id);
  try {
    socket.send(JSON.stringify({ event: "join_room", id }));
  } catch (error) {
    console.error("Websocket error...", error.toString());
  }
}

function leaveRoom() {
  console.debug("leaveRoom");
  try {
    socket.send(JSON.stringify({ event: "leave_room" }));
    store.set(undefined);
  } catch (error) {
    console.error("Websocket error...", error.toString());
  }
}

export default {
  subscribe: store.subscribe,
  create: createRoom,
  join: joinRoom,
  leave: leaveRoom,
}