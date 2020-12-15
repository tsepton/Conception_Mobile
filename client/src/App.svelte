<script>
  import { Router, Route } from "svelte-routing";
  import Home from "./pages/Home.svelte";
  import Room from "./pages/Room.svelte";

  export let url = "";

  let roomId;
  $: url, (roomId = window.location.href.split("/").pop());
</script>

<style>
  :global(body) {
    --primary: #140e45;
    --secondary: #25178a;
  }
</style>

<Router {url}>
  <Route path="/">
    <Home />
  </Route>
  <Route>
    <Room id={roomId} />
  </Route>
</Router>
