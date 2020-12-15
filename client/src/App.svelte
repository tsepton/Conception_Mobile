<script>
  import { Router, Route } from "svelte-routing";
  import Home from "./pages/Home.svelte";
  import Room from "./pages/Room.svelte";

  export let url = "";
</script>

<style>
  :global(body) {
    --back1: #140e45;
    --back2: #25178a;
    --highlight1: #11998e;
    --highlight2: #38ef7d;
    --error: #f71735;
    overflow: hidden;
  }
</style>

<Router {url}>
  <Route path="/">
    <Home />
  </Route>
  <Route>
    <Room />
  </Route>
</Router>
