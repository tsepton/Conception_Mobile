import socket from "./websockets.js";
import { navigate } from "svelte-routing";
import { writable } from 'svelte/store';

const store = writable([]);

socket.onmessage = (e) => {
  const message = JSON.parse(e.data);
  switch (message.event) {
    case "rooms":
      console.debug("rooms", message.rooms);
      store.set(message.rooms);
      break;
    case "enter_room":
      // FIXME: This will bug if we use the store outside of the router's scope
      console.debug("room", message.room);
      store.update((rooms) => [... new Set([...rooms, message.room])]);
      navigate(message.room);
      break;
  }
}

function createRoom() {
  console.debug("createRoom");
  socket.send(JSON.stringify({ event: "create_room" }));
}

function joinRoom(id) {
  console.debug("joinRoom");
  socket.send(JSON.stringify({ event: "join_room", id }));
}

export default {
  subscribe: store.subscribe,
  create: createRoom,
  join: joinRoom,
}