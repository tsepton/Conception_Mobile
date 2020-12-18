import { writable } from 'svelte/store';

const store = writable([
  {
    id: 1,
    title: "Title",
    body: "Enter your text here",
  },
]);

// TODO :  Link with backend
function add() {
  // const lastCard = cards.pop();
  // if (lastCard) {
  //   cards = [
  //     ...cards,
  //     lastCard,
  //     { id: lastCard.id + 1, title: "Title", body: "Enter your text here" },
  //   ];
  // } else {
  //   cards = [{ id: 1, title: "Title", body: "Enter your text here" }];
  // }
}

function remove(id) {
  // cards = cards.filter((card) => card.id !== id);
}

export default {
  subscribe: store.subscribe,
  delete: remove,
  add: add,
}