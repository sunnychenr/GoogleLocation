package com.chenr.entity;

import java.util.List;

/**
 * Created by ChenR on 2016/11/17.
 */

public class AddressResponse {

    /**
     * results : [{"address_components":[{"long_name":"219","short_name":"219","types":["street_number"]},{"long_name":"天华二路","short_name":"天华二路","types":["route"]},{"long_name":"武侯区","short_name":"武侯区","types":["political","sublocality","sublocality_level_1"]},{"long_name":"成都市","short_name":"成都市","types":["locality","political"]},{"long_name":"四川省","short_name":"四川省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国四川省成都市武侯区天华二路219号","geometry":{"location":{"lat":30.541284,"lng":104.06984},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":30.5426329802915,"lng":104.0711889802915},"southwest":{"lat":30.5399350197085,"lng":104.0684910197085}}},"place_id":"ChIJgRGfjUzG7zYRkAb9Y7Qn73c","types":["street_address"]},{"address_components":[{"long_name":"武侯区","short_name":"武侯区","types":["political","sublocality","sublocality_level_1"]},{"long_name":"成都市","short_name":"成都市","types":["locality","political"]},{"long_name":"四川省","short_name":"四川省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国四川省成都市武侯区","geometry":{"bounds":{"northeast":{"lat":30.66429369999999,"lng":104.0942346},"southwest":{"lat":30.5264893,"lng":103.9478826}},"location":{"lat":30.641982,"lng":104.04339},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":30.66429369999999,"lng":104.0942346},"southwest":{"lat":30.5264893,"lng":103.9478826}}},"place_id":"ChIJdzrZjhHE7zYRkJNoWapOqcY","types":["political","sublocality","sublocality_level_1"]},{"address_components":[{"long_name":"成都市","short_name":"成都市","types":["locality","political"]},{"long_name":"四川省","short_name":"四川省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国四川省成都市","geometry":{"bounds":{"northeast":{"lat":31.4353347,"lng":104.8927951},"southwest":{"lat":30.0942976,"lng":102.9928859}},"location":{"lat":30.572816,"lng":104.066801},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":30.8581695,"lng":104.3261719},"southwest":{"lat":30.4588876,"lng":103.8249207}}},"place_id":"ChIJIXdEACPF7zYRAg4kLs5Shrk","types":["locality","political"]},{"address_components":[{"long_name":"四川省","short_name":"四川省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国四川省","geometry":{"bounds":{"northeast":{"lat":34.313,"lng":108.5467122},"southwest":{"lat":26.0458656,"lng":97.34808149999999}},"location":{"lat":30.651226,"lng":104.075881},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":34.3129469,"lng":108.5467122},"southwest":{"lat":26.0458656,"lng":97.34808149999999}}},"place_id":"ChIJs829aDPn5DYRuf6Zj898j94","types":["administrative_area_level_1","political"]},{"address_components":[{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国","geometry":{"bounds":{"northeast":{"lat":53.56097399999999,"lng":134.7728099},"southwest":{"lat":17.9996,"lng":73.4994136}},"location":{"lat":35.86166,"lng":104.195397},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":53.55493999999999,"lng":134.7717741},"southwest":{"lat":18.1576156,"lng":73.4994136}}},"place_id":"ChIJwULG5WSOUDERbzafNHyqHZU","types":["country","political"]}]
     * status : OK
     */

    private String status;
    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address_components : [{"long_name":"219","short_name":"219","types":["street_number"]},{"long_name":"天华二路","short_name":"天华二路","types":["route"]},{"long_name":"武侯区","short_name":"武侯区","types":["political","sublocality","sublocality_level_1"]},{"long_name":"成都市","short_name":"成都市","types":["locality","political"]},{"long_name":"四川省","short_name":"四川省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}]
         * formatted_address : 中国四川省成都市武侯区天华二路219号
         * geometry : {"location":{"lat":30.541284,"lng":104.06984},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":30.5426329802915,"lng":104.0711889802915},"southwest":{"lat":30.5399350197085,"lng":104.0684910197085}}}
         * place_id : ChIJgRGfjUzG7zYRkAb9Y7Qn73c
         * types : ["street_address"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * location : {"lat":30.541284,"lng":104.06984}
             * location_type : ROOFTOP
             * viewport : {"northeast":{"lat":30.5426329802915,"lng":104.0711889802915},"southwest":{"lat":30.5399350197085,"lng":104.0684910197085}}
             */

            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class LocationBean {
                /**
                 * lat : 30.541284
                 * lng : 104.06984
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":30.5426329802915,"lng":104.0711889802915}
                 * southwest : {"lat":30.5399350197085,"lng":104.0684910197085}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 30.5426329802915
                     * lng : 104.0711889802915
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 30.5399350197085
                     * lng : 104.0684910197085
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : 219
             * short_name : 219
             * types : ["street_number"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
