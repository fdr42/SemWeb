<template>
  <div>
    <h1>Voici les station disponibles à Saint Étienne :</h1>
    <ul v-for="e in listStation" v-bind:key="e.id">
      <li>Station : {{e}}</li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'StEtienne',
    data: function() {
    return {
      listStation: ["fouchette", "arbre", "fabien", "rené"],
      message: 'You loaded this page on ' + new Date().toLocaleString()
    };
}
  }
</script>