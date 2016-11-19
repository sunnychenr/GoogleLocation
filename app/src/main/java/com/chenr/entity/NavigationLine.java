package com.chenr.entity;

import java.util.List;

/**
 * Created by ChenR on 2016/11/19.
 */

public class NavigationLine {


    /**
     * geocoded_waypoints : [{"geocoder_status":"OK","place_id":"ChIJgRGfjUzG7zYRkAb9Y7Qn73c","types":["street_address"]},{"geocoder_status":"OK","place_id":"ChIJdXKLhkrG7zYRWAZHmz1R4RA","types":["street_address"]}]
     * routes : [{"bounds":{"northeast":{"lat":30.5423938,"lng":104.0766023},"southwest":{"lat":30.5410204,"lng":104.070378}},"copyrights":"地图数据 ©2016 GS(2011)6020","legs":[{"distance":{"text":"0.7 公里","value":722},"duration":{"text":"9分钟","value":526},"end_address":"中国四川省成都市武侯区天华二路133号","end_location":{"lat":30.5410204,"lng":104.0766023},"start_address":"中国四川省成都市武侯区天华二路219号","start_location":{"lat":30.5419031,"lng":104.070378},"steps":[{"distance":{"text":"63 米","value":63},"duration":{"text":"1分钟","value":44},"end_location":{"lat":30.5419048,"lng":104.0710337},"html_instructions":"向<b>东<\/b>前行","polyline":{"points":"{elyD{fezR?aC"},"start_location":{"lat":30.5419031,"lng":104.070378},"travel_mode":"WALKING"},{"distance":{"text":"49 米","value":49},"duration":{"text":"1分钟","value":35},"end_location":{"lat":30.5423453,"lng":104.0710014},"html_instructions":"向<b>左<\/b>转，前往<b>天华二路<\/b>/<b>拓新东街<\/b>","maneuver":"turn-left","polyline":{"points":"{elyD}jezRMAm@D]@"},"start_location":{"lat":30.5419048,"lng":104.0710337},"travel_mode":"WALKING"},{"distance":{"text":"0.6 公里","value":610},"duration":{"text":"7分钟","value":447},"end_location":{"lat":30.5410204,"lng":104.0766023},"html_instructions":"向<b>右<\/b>转，进入<b>天华二路<\/b>/<b>拓新东街<\/b><div style=\"font-size:0.9em\">继续沿天华二路前行<\/div><div style=\"font-size:0.9em\">目的地在右侧<\/div>","maneuver":"turn-right","polyline":{"points":"uhlyDwjezREuD?_@BwEE{@@uE@cB?]?K?G@OBMFKHGz@i@f@]b@Y^[f@k@d@m@"},"start_location":{"lat":30.5423453,"lng":104.0710014},"travel_mode":"WALKING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"{elyD{fezR?aCMAkAFEuEBwEE{@ByH?i@@WJYpCiBfAgAd@m@"},"summary":"天华二路","warnings":["步行路线正在测试中。 注意 \u2013 此路线可能缺乏部分人行道信息。"],"waypoint_order":[]}]
     * status : OK
     */

    private String status;
    private List<GeocodedWaypointsBean> geocoded_waypoints;
    private List<RoutesBean> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeocodedWaypointsBean> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<GeocodedWaypointsBean> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public static class GeocodedWaypointsBean {
        /**
         * geocoder_status : OK
         * place_id : ChIJgRGfjUzG7zYRkAb9Y7Qn73c
         * types : ["street_address"]
         */

        private String geocoder_status;
        private String place_id;
        private List<String> types;

        public String getGeocoder_status() {
            return geocoder_status;
        }

        public void setGeocoder_status(String geocoder_status) {
            this.geocoder_status = geocoder_status;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    public static class RoutesBean {
        /**
         * bounds : {"northeast":{"lat":30.5423938,"lng":104.0766023},"southwest":{"lat":30.5410204,"lng":104.070378}}
         * copyrights : 地图数据 ©2016 GS(2011)6020
         * legs : [{"distance":{"text":"0.7 公里","value":722},"duration":{"text":"9分钟","value":526},"end_address":"中国四川省成都市武侯区天华二路133号","end_location":{"lat":30.5410204,"lng":104.0766023},"start_address":"中国四川省成都市武侯区天华二路219号","start_location":{"lat":30.5419031,"lng":104.070378},"steps":[{"distance":{"text":"63 米","value":63},"duration":{"text":"1分钟","value":44},"end_location":{"lat":30.5419048,"lng":104.0710337},"html_instructions":"向<b>东<\/b>前行","polyline":{"points":"{elyD{fezR?aC"},"start_location":{"lat":30.5419031,"lng":104.070378},"travel_mode":"WALKING"},{"distance":{"text":"49 米","value":49},"duration":{"text":"1分钟","value":35},"end_location":{"lat":30.5423453,"lng":104.0710014},"html_instructions":"向<b>左<\/b>转，前往<b>天华二路<\/b>/<b>拓新东街<\/b>","maneuver":"turn-left","polyline":{"points":"{elyD}jezRMAm@D]@"},"start_location":{"lat":30.5419048,"lng":104.0710337},"travel_mode":"WALKING"},{"distance":{"text":"0.6 公里","value":610},"duration":{"text":"7分钟","value":447},"end_location":{"lat":30.5410204,"lng":104.0766023},"html_instructions":"向<b>右<\/b>转，进入<b>天华二路<\/b>/<b>拓新东街<\/b><div style=\"font-size:0.9em\">继续沿天华二路前行<\/div><div style=\"font-size:0.9em\">目的地在右侧<\/div>","maneuver":"turn-right","polyline":{"points":"uhlyDwjezREuD?_@BwEE{@@uE@cB?]?K?G@OBMFKHGz@i@f@]b@Y^[f@k@d@m@"},"start_location":{"lat":30.5423453,"lng":104.0710014},"travel_mode":"WALKING"}],"traffic_speed_entry":[],"via_waypoint":[]}]
         * overview_polyline : {"points":"{elyD{fezR?aCMAkAFEuEBwEE{@ByH?i@@WJYpCiBfAgAd@m@"}
         * summary : 天华二路
         * warnings : ["步行路线正在测试中。 注意 \u2013 此路线可能缺乏部分人行道信息。"]
         * waypoint_order : []
         */

        private BoundsBean bounds;
        private String copyrights;
        private OverviewPolylineBean overview_polyline;
        private String summary;
        private List<LegsBean> legs;
        private List<String> warnings;
        private List<?> waypoint_order;

        public BoundsBean getBounds() {
            return bounds;
        }

        public void setBounds(BoundsBean bounds) {
            this.bounds = bounds;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }

        public OverviewPolylineBean getOverview_polyline() {
            return overview_polyline;
        }

        public void setOverview_polyline(OverviewPolylineBean overview_polyline) {
            this.overview_polyline = overview_polyline;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<LegsBean> getLegs() {
            return legs;
        }

        public void setLegs(List<LegsBean> legs) {
            this.legs = legs;
        }

        public List<String> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<String> warnings) {
            this.warnings = warnings;
        }

        public List<?> getWaypoint_order() {
            return waypoint_order;
        }

        public void setWaypoint_order(List<?> waypoint_order) {
            this.waypoint_order = waypoint_order;
        }

        public static class BoundsBean {
            /**
             * northeast : {"lat":30.5423938,"lng":104.0766023}
             * southwest : {"lat":30.5410204,"lng":104.070378}
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
                 * lat : 30.5423938
                 * lng : 104.0766023
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
                 * lat : 30.5410204
                 * lng : 104.070378
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

        public static class OverviewPolylineBean {
            /**
             * points : {elyD{fezR?aCMAkAFEuEBwEE{@ByH?i@@WJYpCiBfAgAd@m@
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }

        public static class LegsBean {
            /**
             * distance : {"text":"0.7 公里","value":722}
             * duration : {"text":"9分钟","value":526}
             * end_address : 中国四川省成都市武侯区天华二路133号
             * end_location : {"lat":30.5410204,"lng":104.0766023}
             * start_address : 中国四川省成都市武侯区天华二路219号
             * start_location : {"lat":30.5419031,"lng":104.070378}
             * steps : [{"distance":{"text":"63 米","value":63},"duration":{"text":"1分钟","value":44},"end_location":{"lat":30.5419048,"lng":104.0710337},"html_instructions":"向<b>东<\/b>前行","polyline":{"points":"{elyD{fezR?aC"},"start_location":{"lat":30.5419031,"lng":104.070378},"travel_mode":"WALKING"},{"distance":{"text":"49 米","value":49},"duration":{"text":"1分钟","value":35},"end_location":{"lat":30.5423453,"lng":104.0710014},"html_instructions":"向<b>左<\/b>转，前往<b>天华二路<\/b>/<b>拓新东街<\/b>","maneuver":"turn-left","polyline":{"points":"{elyD}jezRMAm@D]@"},"start_location":{"lat":30.5419048,"lng":104.0710337},"travel_mode":"WALKING"},{"distance":{"text":"0.6 公里","value":610},"duration":{"text":"7分钟","value":447},"end_location":{"lat":30.5410204,"lng":104.0766023},"html_instructions":"向<b>右<\/b>转，进入<b>天华二路<\/b>/<b>拓新东街<\/b><div style=\"font-size:0.9em\">继续沿天华二路前行<\/div><div style=\"font-size:0.9em\">目的地在右侧<\/div>","maneuver":"turn-right","polyline":{"points":"uhlyDwjezREuD?_@BwEE{@@uE@cB?]?K?G@OBMFKHGz@i@f@]b@Y^[f@k@d@m@"},"start_location":{"lat":30.5423453,"lng":104.0710014},"travel_mode":"WALKING"}]
             * traffic_speed_entry : []
             * via_waypoint : []
             */

            private DistanceBean distance;
            private DurationBean duration;
            private String end_address;
            private EndLocationBean end_location;
            private String start_address;
            private StartLocationBean start_location;
            private List<StepsBean> steps;
            private List<?> traffic_speed_entry;
            private List<?> via_waypoint;

            public DistanceBean getDistance() {
                return distance;
            }

            public void setDistance(DistanceBean distance) {
                this.distance = distance;
            }

            public DurationBean getDuration() {
                return duration;
            }

            public void setDuration(DurationBean duration) {
                this.duration = duration;
            }

            public String getEnd_address() {
                return end_address;
            }

            public void setEnd_address(String end_address) {
                this.end_address = end_address;
            }

            public EndLocationBean getEnd_location() {
                return end_location;
            }

            public void setEnd_location(EndLocationBean end_location) {
                this.end_location = end_location;
            }

            public String getStart_address() {
                return start_address;
            }

            public void setStart_address(String start_address) {
                this.start_address = start_address;
            }

            public StartLocationBean getStart_location() {
                return start_location;
            }

            public void setStart_location(StartLocationBean start_location) {
                this.start_location = start_location;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            public List<?> getTraffic_speed_entry() {
                return traffic_speed_entry;
            }

            public void setTraffic_speed_entry(List<?> traffic_speed_entry) {
                this.traffic_speed_entry = traffic_speed_entry;
            }

            public List<?> getVia_waypoint() {
                return via_waypoint;
            }

            public void setVia_waypoint(List<?> via_waypoint) {
                this.via_waypoint = via_waypoint;
            }

            public static class DistanceBean {
                /**
                 * text : 0.7 公里
                 * value : 722
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class DurationBean {
                /**
                 * text : 9分钟
                 * value : 526
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class EndLocationBean {
                /**
                 * lat : 30.5410204
                 * lng : 104.0766023
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

            public static class StartLocationBean {
                /**
                 * lat : 30.5419031
                 * lng : 104.070378
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

            public static class StepsBean {
                /**
                 * distance : {"text":"63 米","value":63}
                 * duration : {"text":"1分钟","value":44}
                 * end_location : {"lat":30.5419048,"lng":104.0710337}
                 * html_instructions : 向<b>东</b>前行
                 * polyline : {"points":"{elyD{fezR?aC"}
                 * start_location : {"lat":30.5419031,"lng":104.070378}
                 * travel_mode : WALKING
                 * maneuver : turn-left
                 */

                private DistanceBeanX distance;
                private DurationBeanX duration;
                private EndLocationBeanX end_location;
                private String html_instructions;
                private PolylineBean polyline;
                private StartLocationBeanX start_location;
                private String travel_mode;
                private String maneuver;

                public DistanceBeanX getDistance() {
                    return distance;
                }

                public void setDistance(DistanceBeanX distance) {
                    this.distance = distance;
                }

                public DurationBeanX getDuration() {
                    return duration;
                }

                public void setDuration(DurationBeanX duration) {
                    this.duration = duration;
                }

                public EndLocationBeanX getEnd_location() {
                    return end_location;
                }

                public void setEnd_location(EndLocationBeanX end_location) {
                    this.end_location = end_location;
                }

                public String getHtml_instructions() {
                    return html_instructions;
                }

                public void setHtml_instructions(String html_instructions) {
                    this.html_instructions = html_instructions;
                }

                public PolylineBean getPolyline() {
                    return polyline;
                }

                public void setPolyline(PolylineBean polyline) {
                    this.polyline = polyline;
                }

                public StartLocationBeanX getStart_location() {
                    return start_location;
                }

                public void setStart_location(StartLocationBeanX start_location) {
                    this.start_location = start_location;
                }

                public String getTravel_mode() {
                    return travel_mode;
                }

                public void setTravel_mode(String travel_mode) {
                    this.travel_mode = travel_mode;
                }

                public String getManeuver() {
                    return maneuver;
                }

                public void setManeuver(String maneuver) {
                    this.maneuver = maneuver;
                }

                public static class DistanceBeanX {
                    /**
                     * text : 63 米
                     * value : 63
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                public static class DurationBeanX {
                    /**
                     * text : 1分钟
                     * value : 44
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                public static class EndLocationBeanX {
                    /**
                     * lat : 30.5419048
                     * lng : 104.0710337
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

                public static class PolylineBean {
                    /**
                     * points : {elyD{fezR?aC
                     */

                    private String points;

                    public String getPoints() {
                        return points;
                    }

                    public void setPoints(String points) {
                        this.points = points;
                    }
                }

                public static class StartLocationBeanX {
                    /**
                     * lat : 30.5419031
                     * lng : 104.070378
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
    }
}
