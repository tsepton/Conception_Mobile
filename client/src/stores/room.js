import wsStore from "./websockets.js";
import { writable } from 'svelte/store';

const store = writable(undefined);
wsStore.subscribe(( ) => {
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
  try {
    socket.send(JSON.stringify({ event: "create_room" }));
  } catch (error) {
    console.error("Websocket error...", error.toString());
  }
}

function joinRoom(id) {
  try {
    socket.send(JSON.stringify({ event: "join_room", id }));
  } catch (error) {
    console.error("Websocket error...", error.toString());
  }
}

function leaveRoom() {
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