$(document).ready(function () {
    $('#example').DataTable();
});

// On initialise la latitude et la longitude de Paris (centre de la carte)

var macarte = null;
var macarteProximite = null;


var markerClusters;

// Fonction d'initialisation de la carte
function initMap() {
    // Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"

    var greenIcon = new L.Icon({
        iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });
    var redIcon = new L.Icon({
        iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });
    macarte = L.map('map').setView([20, 70], 1.5);
    markerClusters = L.markerClusterGroup();
    // Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
    L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
        // Il est toujours bien de laisser le lien vers la source des données
        attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
        minZoom: 1,
        maxZoom: 20
    }).addTo(macarte);
    var j=0;
    for (station in listStation) {
        var latPoint = listStation[j].latitude;
        var lonPoint = listStation[j].longitude;
        var nameStation = listStation[j].stationName;
        var detail = listStation[j].address;
        var idStation = listStation[j].stationId;
        var city = listStation[j].locationCity;
        var capacity = listStation[j].capacity;
        latPoint = parseFloat(latPoint);
        lonPoint = parseFloat(lonPoint);
        var marker = L.marker([latPoint, lonPoint]);

        marker.nameStation = nameStation;
        marker.detail = detail;
        marker.latitude = latPoint;
        marker.longitude = lonPoint;
        marker.city = city.replace(" ","-");
        marker.idStation = idStation;
        marker.capacity = capacity;

        marker.on('click', function () {
            var stationActuelle = this.nameStation;
            document.getElementById("available").innerHTML="Chargement";
            if (macarteProximite != undefined || macarteProximite != null) {
                macarteProximite.remove();
            }

            macarteProximite = L.map('mapProximite').setView([this.latitude, this.longitude], 16);

            // Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
            L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
                // Il est toujours bien de laisser le lien vers la source des données
                attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
                minZoom: 1,
                maxZoom: 20
            }).addTo(macarteProximite);

            L.marker([this.latitude, this.longitude]).addTo(macarteProximite);

            axios.get('http://localhost:8080/availableRest?id=' + this.idStation + '&city=' + this.city)
                .then(function (response) {
                    console.log("NumValides : " + response.data);

                    document.getElementById("available").innerHTML = response.data;

                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
                .finally(function () {
                    // always executed
                });


            axios.get('http://localhost:8080/proximityRest?lon=' + this.longitude + '&lat=' + this.latitude)
                .then(function (response) {
                    var i = 0;

                    var results = response.data;
                    console.log(results);
                    for (var item in results) {
                        var placeLabel = results[i].placeLabel;
                        console.log(placeLabel + "  " + i);

                        var icone = greenIcon;

                        if ("restaurant" == results[i].instance_ofLabel) {
                            icone = redIcon;
                        }

                        var markProx = L.marker([response.data[i].latitude, response.data[i].longitude], {
                            icon: icone,
                            title: placeLabel
                        });
                        markProx.bindPopup(
                            "<img src=\"" + results[i].image + "\" alt=\"" + results[i].instance_ofLabel + "\"style=\"width:100%;height:200px;\">" +
                            "<h6 class='p-2'>" + placeLabel + "</h6><span class='p-3'>" + results[i].placeDescription + ".</span> Se situe à " + results[i].dist * 1000 + "m de la station " + stationActuelle
                        ).openPopup();
                        markProx.addTo(macarteProximite);
                        i++;
                    }


                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
                .finally(function () {
                    // always executed
                });

            document.getElementById("stationId").setAttribute("about", "http://ex.com/"+this.city+"/"+this.idStation);
            document.getElementById("locationCity").innerHTML ="<a href=\"http://dbpedia.org/resource/"+this.city+"\">"+this.city+"</a>";
            document.getElementById("stationLabel").innerHTML = this.nameStation;
            document.getElementById("stationDetail").innerHTML = this.detail;
            document.getElementById("stationCapacity").innerHTML = this.capacity;
            document.getElementById("stationLat").innerHTML = this.latitude;
            document.getElementById("stationLon").innerHTML = this.longitude;
            document.getElementById("stationLon").innerHTML = this.longitude;


            $('#modalProximite').on('show.bs.modal', function () {
                setTimeout(function () {
                    macarteProximite.invalidateSize();
                }, 10);
            });
            $('#modalProximite').modal('show');

        });

        markerClusters.addLayer(marker);
        j++;
    }

    macarte.addLayer(markerClusters);

}
function setView(lat, lon,zoom){

   macarte.setView([lat, lon], zoom);
}

window.onload = function () {
    // Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
    initMap();
};

$('*').contents().each(function() {
    if(this.nodeType === Node.COMMENT_NODE) {
        $(this).remove();
    }
});