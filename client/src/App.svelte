<script>
  import { Router, Route } from "svelte-routing";
  import Home from "./pages/Home.svelte";
  import Join from "./pages/Join.svelte";
  import Room from "./pages/Room.svelte";

  export let url = "";

  let roomId;
  $: url, (roomId = window.location.href.split("/").pop());
</script>

<style>
  :global(body) {
    --back1: #140e45;
    --back2: #25178a;
    --highlight1: #11998e;
    --highlight2: #38EF7D;
    --error: #F71735;
    overflow: hidden;
  }
</style>

<Router {url}>
  <Route path="/">
    <Home />
  </Route>
  <Route path="join">
    <Join />
  </Route>
  <Route>
    <Room id={roomId} />
  </Route>
</Router>
