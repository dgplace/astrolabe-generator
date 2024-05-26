package com.wymarc.astrolabe.generator.printengines.postscript.extras.horary;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.math.AstroMath;
import com.wymarc.astrolabe.math.InterSect;

import java.util.Calendar;

/**
 * This Plugin will calculate the components of A quadrans ventus and
 * print the results to an Encapsulated PostScript (EPS) file.
 *
 * <p>
 * link      http://astrolabeproject.com
 * link      http://www.astrolabes.org
 */

public class QuadransVetus {

    private Astrolabe myAstrolabe = new Astrolabe();

    private String drawOutline() {
        StringBuilder out = new StringBuilder();

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("1 0 0 setrgbcolor");
        }

        out.append("\n").append("% draw outlines")
                .append("\n").append("-36 36 translate")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("0 -540 lineto")
                .append("\n").append("36 -540 lineto")
                .append("\n").append("36 -36 504 270 360 arc")
                .append("\n").append("540 0 lineto")
                .append("\n").append("0 0 lineto stroke");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        out.append("\n").append("% draw inner outlines")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 moveto")
                .append("\n").append("36 -535 lineto")
                .append("\n").append("36 -36 499 270 360 arc")
                .append("\n").append("36 -36 lineto stroke");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 setgray");
        }

        return out.toString();
    }


    private String drawDegreeScale() {
        StringBuilder out = new StringBuilder();

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        // draw arcs
        out.append("\n").append("% degree scale")
                .append("\n").append("0 0 489 270 360 arc stroke")
                .append("\n").append("0 0 460 270 360 arc stroke")
                .append("\n").append("0 0 455 270 360 arc stroke")
                .append("\n");

        // create 1 degree marks
        for (int count = 0; count <= 89; count++) {
            if (count > 0){
                if (count % 5 == 0) { // longer mark at each 5 degrees
                    out.append("\n").append("460 0 moveto");
                }else{
                    out.append("\n").append("489 0 moveto");
                }
                out.append("\n").append("499 0 lineto stroke");
            }
            out.append("\n").append("-1 rotate");
        }
        out.append("\n").append("89 rotate");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 setgray");
        }

        //Mark degrees
        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("ArialFont16 setfont");
        }else{
            out.append("\n").append("NormalFont16 setfont");
        }

        for (int count = 1; count <= 18; count++) {
            out.append(EPSToolKit.drawInsideCircularText((count * 5) + "", 16, (-89 + (count * 5)) - 2.5, 480));
        }

        return out.toString();
    }

    private String drawTick(double alt, int start, int end, String text) {
        StringBuilder out = new StringBuilder();

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        out.append("\n").append(alt).append(" rotate")
                .append("\n").append("0 ").append(start).append(" moveto")
                .append("\n").append("0 ").append(end).append(" lineto stroke")
                .append("\n").append(-alt).append(" rotate");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 setgray");
        }

        if (!text.equals("")) {
            if (myAstrolabe.getLaserSupport()){
                out.append("\n").append("ArialFont10 setfont");
            }else{
                out.append("\n").append("NormalFont10 setfont");
            }
            if (end == 390) {
                out.append(EPSToolKit.drawInsideCircularText(text, 10, alt + 90, 403));
            } else if (start == 435) {
                out.append(EPSToolKit.drawOutsideCircularText(text, 10, alt + 90, 421));
            }
        }
        return out.toString();
    }

    private String drawCalendarScale(double lat) {
        StringBuilder out = new StringBuilder();
        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        // draw arcs
        out.append("\n").append("% Space for calendar")
                .append("\n").append("0 0 370 270 360 arc stroke")
                .append("\n").append("0 0 365 270 360 arc stroke")
                .append("\n");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 setgray");
        }


        double alt;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        // draw arcs
        out.append("\n").append("% Calendar scale");

        alt = AstroMath.solarNoonAltitude(11, 22, year, lat);
        out.append(drawTick(alt - 183, 370, 455, ""));
        out.append(drawTick(alt - 184, 370, 455, ""));
        double lowAngle = (alt - 3) + 270;

        alt = AstroMath.solarNoonAltitude(5, 21, year, lat);
        out.append(drawTick(alt - 177, 370, 455, ""));
        out.append(drawTick(alt - 176, 370, 455, ""));
        double highAngle = (alt + 3) + 270;

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        out.append("\n").append("\n").append("0 0 390 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 410 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 415 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 435 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke");
        //If printing the calendar separately
//        out.append("\n").append("0 0 370 ").append(lowAngle - 1).append(" ").append(highAngle + 1).append(" arc stroke")
//                .append("\n").append("0 0 390 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
//                .append("\n").append("0 0 410 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
//                .append("\n").append("0 0 415 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
//                .append("\n").append("0 0 435 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
//                .append("\n").append("0 0 455 ").append(lowAngle - 1).append(" ").append(highAngle + 1).append(" arc stroke");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 setgray");
        }

        //january
        alt = AstroMath.solarNoonAltitude(0, 1, year, lat) - 180;
        out.append(drawTick(alt, 370, 410, ""));
        alt = AstroMath.solarNoonAltitude(0, 5, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(0, 10, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(0, 15, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(0, 20, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, "January"));
        alt = AstroMath.solarNoonAltitude(0, 25, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(0, 30, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));

        //february
        alt = AstroMath.solarNoonAltitude(1, 1, year, lat) - 180;
        out.append(drawTick(alt, 370, 410, ""));
        alt = AstroMath.solarNoonAltitude(1, 5, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(1, 10, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(1, 15, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, "February"));
        alt = AstroMath.solarNoonAltitude(1, 20, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(1, 25, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));

        //mafrch
        alt = AstroMath.solarNoonAltitude(2, 1, year, lat) - 180;
        out.append(drawTick(alt, 370, 410, ""));
        alt = AstroMath.solarNoonAltitude(2, 5, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(2, 10, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(2, 15, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, "March"));
        alt = AstroMath.solarNoonAltitude(2, 20, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(2, 25, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(2, 30, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));

        //april
        alt = AstroMath.solarNoonAltitude(3, 1, year, lat) - 180;
        out.append(drawTick(alt, 370, 410, ""));
        alt = AstroMath.solarNoonAltitude(3, 5, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(3, 10, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(3, 15, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, "April"));
        alt = AstroMath.solarNoonAltitude(3, 20, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(3, 25, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));

        //may
        alt = AstroMath.solarNoonAltitude(4, 1, year, lat) - 180;
        out.append(drawTick(alt, 370, 410, ""));
        alt = AstroMath.solarNoonAltitude(4, 5, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(4, 10, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(4, 15, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, "May"));
        alt = AstroMath.solarNoonAltitude(4, 20, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(4, 25, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(4, 30, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));

        //june
        alt = AstroMath.solarNoonAltitude(5, 1, year, lat) - 180;
        out.append(drawTick(alt, 370, 410, "Ju"));
        alt = AstroMath.solarNoonAltitude(5, 5, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(5, 10, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));
        alt = AstroMath.solarNoonAltitude(5, 15, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(5, 20, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, "Jun"));

        //reverse
        alt = AstroMath.solarNoonAltitude(5, 25, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));

        //july
        alt = AstroMath.solarNoonAltitude(6, 1, year, lat) - 180;
        out.append(drawTick(alt, 415, 455, ""));
        alt = AstroMath.solarNoonAltitude(6, 5, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(6, 10, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(6, 15, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(6, 20, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, "July"));
        alt = AstroMath.solarNoonAltitude(6, 25, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(6, 30, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));

        //august
        alt = AstroMath.solarNoonAltitude(7, 1, year, lat) - 180;
        out.append(drawTick(alt, 415, 455, ""));
        alt = AstroMath.solarNoonAltitude(7, 5, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(7, 10, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(7, 15, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, "August"));
        alt = AstroMath.solarNoonAltitude(7, 20, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(7, 25, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(7, 30, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));

        //september
        alt = AstroMath.solarNoonAltitude(8, 1, year, lat) - 180;
        out.append(drawTick(alt, 415, 455, ""));
        alt = AstroMath.solarNoonAltitude(8, 5, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(8, 10, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(8, 15, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, "September"));
        alt = AstroMath.solarNoonAltitude(8, 20, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(8, 25, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));

        //october
        alt = AstroMath.solarNoonAltitude(9, 1, year, lat) - 180;
        out.append(drawTick(alt, 415, 455, ""));
        alt = AstroMath.solarNoonAltitude(9, 5, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(9, 10, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(9, 15, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, "October"));
        alt = AstroMath.solarNoonAltitude(9, 20, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(9, 25, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(9, 30, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));

        //november
        alt = AstroMath.solarNoonAltitude(10, 1, year, lat) - 180;
        out.append(drawTick(alt, 415, 455, ""));
        alt = AstroMath.solarNoonAltitude(10, 5, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(10, 10, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(10, 15, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, "November"));
        alt = AstroMath.solarNoonAltitude(10, 20, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(10, 25, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));

        //december
        alt = AstroMath.solarNoonAltitude(11, 1, year, lat) - 180;
        out.append(drawTick(alt, 415, 455, ""));
        alt = AstroMath.solarNoonAltitude(11, 5, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(11, 10, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, ""));
        alt = AstroMath.solarNoonAltitude(11, 15, year, lat) - 180;
        out.append(drawTick(alt, 435, 440, ""));
        alt = AstroMath.solarNoonAltitude(11, 20, year, lat) - 180;
        out.append(drawTick(alt, 435, 444, "Dec"));
        //reverse
        alt = AstroMath.solarNoonAltitude(11, 25, year, lat) - 180;
        out.append(drawTick(alt, 385, 390, ""));
        alt = AstroMath.solarNoonAltitude(11, 30, year, lat) - 180;
        out.append(drawTick(alt, 381, 390, ""));



        return out.toString();
    }

    /**
     * computes and draws the shadow squares
     *
     * @return returns the ps code for drawing the Shadow Squares
     */
    private String drawShadowSquare() {
        //compute size of box
        double shadowRadius = 365.0;
        double shadowSide = Math.sqrt((shadowRadius * shadowRadius) / 2.0); //from pythagoras
        double div = 12.0;
        StringBuilder out = new StringBuilder();

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        out.append("\n").append("% Shadow square")
                .append("\n").append("% =============== Create Shadow Square =================")
                .append("\n").append("newpath")
                .append("\n").append(shadowSide).append(" 0 moveto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto")
                .append("\n").append("0 ").append(-shadowSide).append(" lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append(shadowSide - 10).append(" 0 moveto")
                .append("\n").append(shadowSide - 10).append(" ").append(-(shadowSide - 10)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 10)).append(" lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append(shadowSide - 24).append(" 0 moveto")
                .append("\n").append(shadowSide - 24).append(" ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 24)).append(" lineto stroke");

        for (int count = 1; count < div; count++) {// print division lines
            // determine angle
            double angle = (45.0/12.0) * count;
            // determine long and short mark intersections with sides
            double outside = Math.tan(Math.toRadians(angle)) * shadowSide;
            double middle = Math.tan(Math.toRadians(angle)) * (shadowSide - 10);
            double inside = Math.tan(Math.toRadians(angle)) * (shadowSide - 24);

            if (count == 4 || count == 8){
                out.append("\n").append("newpath")
                        .append("\n").append(shadowSide - 24).append(" ").append(-inside).append(" moveto")
                        .append("\n").append(shadowSide).append(" ").append(-outside).append(" lineto stroke")
                        .append("\n").append(inside).append(" ").append(-(shadowSide - 24)).append(" moveto")
                        .append("\n").append(outside).append(" ").append(-shadowSide).append(" lineto stroke");
            }else{
                out.append("\n").append("newpath")
                        .append("\n").append(shadowSide - 10).append(" ").append(-middle).append(" moveto")
                        .append("\n").append(shadowSide).append(" ").append(-outside).append(" lineto stroke")
                        .append("\n").append(middle).append(" ").append(-(shadowSide - 10)).append(" moveto")
                        .append("\n").append(outside).append(" ").append(-shadowSide).append(" lineto stroke");
            }
        }

        // Draw 45 line
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto stroke");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 setgray");
        }

        // label
        //determine locations of numbers on middle line
        double four = Math.tan(Math.toRadians(7.5)) * (shadowSide - 10);
        double eight = Math.tan(Math.toRadians(22.5)) * (shadowSide - 10);
        double twelve = Math.tan(Math.toRadians(37.5)) * (shadowSide - 10);

        //Mark degrees
        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("ArialFont12 setfont");
        }else{
            out.append("\n").append("NormalFont12 setfont");
        }

        out.append("\n").append("newpath")
                //.append("\n").append("-10 0 moveto")
                .append("\n").append(four).append(" ").append(-(shadowSide - 13)).append(" moveto")
                .append("\n").append(EPSToolKit.centerText("4"))
                .append("\n").append(eight).append(" ").append(-(shadowSide - 13)).append(" moveto")
                .append("\n").append(EPSToolKit.centerText("8"))
                .append("\n").append(twelve).append(" ").append(-(shadowSide - 13)).append(" moveto")
                .append("\n").append(EPSToolKit.centerText("12"));
        out.append("\n").append("90 rotate");
        out.append("\n").append("newpath")
                //.append("\n").append("-10 0 moveto")
                .append("\n").append(-four).append(" ").append(-(shadowSide - 13)).append(" moveto")
                .append("\n").append(EPSToolKit.centerText("4"))
                .append("\n").append(-eight).append(" ").append(-(shadowSide - 13)).append(" moveto")
                .append("\n").append(EPSToolKit.centerText("8"))
                .append("\n").append(-twelve).append(" ").append(-(shadowSide - 13)).append(" moveto")
                .append("\n").append(EPSToolKit.centerText("12"));
        out.append("\n").append("-90 rotate");

        out.append("\n").append("% =============== End Shadow Square =================");

        return out.toString();
    }

    private String drawUnequalHours() {
        StringBuilder out = new StringBuilder();
        // mark unequal hour lines
        // draw arcs
        out.append("\n").append("% unequal hours");

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        double radius;
        for (int count = 1; count <= 6; count++) {
            radius = (365.0 / (2 * (Math.sin(Math.toRadians(15 * count)))));
            InterSect interSect1 = new InterSect(radius, 0.0, radius, 0.0, 0.0, 365.0);
            double angle2 = interSect1.getAngle2();
            out.append("\n").append(radius).append(" 0 ").append(radius).append(" 180 ").append(angle2).append(" arc stroke");
        }

        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("0 setgray");
        }

        //Mark unequal hours
        //Mark degrees
        if (myAstrolabe.getLaserSupport()){
            out.append("\n").append("ArialFont12 setfont");
        }else{
            out.append("\n").append("NormalFont12 setfont");
        }

        out.append(EPSToolKit.drawInsideCircularText("12", 12, (-88), 360));
        out.append(EPSToolKit.drawInsideCircularText("1", 12, (-77), 360));
        out.append(EPSToolKit.drawInsideCircularText("11", 12, (-73), 360));
        out.append(EPSToolKit.drawInsideCircularText("2", 12, (-62.5), 360));
        out.append(EPSToolKit.drawInsideCircularText("10", 12, (-58.5), 360));
        out.append(EPSToolKit.drawInsideCircularText("3", 12, (-48.5), 360));
        out.append(EPSToolKit.drawInsideCircularText("9", 12, (-41.5), 360));
        out.append(EPSToolKit.drawInsideCircularText("4", 12, (-35), 360));
        out.append(EPSToolKit.drawInsideCircularText("8", 12, (-28.5), 360));
        out.append(EPSToolKit.drawInsideCircularText("5", 12, (-23.5), 360));
        out.append(EPSToolKit.drawInsideCircularText("6", 12, (-1), 360));
        out.append(EPSToolKit.drawInsideCircularText("7", 12, (-14.5), 360));

        return out.toString();
    }

    public String printQuadrant(Astrolabe myAstrolabe) {
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Quadrans Vetus Quadrant")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments")

                .append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n")
                .append("\n").append("%% setup")
                //.append(EPSToolKit.fillBackground())
                .append("\n").append("72 630 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n")
                .append(EPSToolKit.setUpFonts())
                .append(EPSToolKit.setUpCircularText())
                .append("\n")
                .append("\n").append("gsave")
                .append(drawDegreeScale())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawOutline())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawCalendarScale(myAstrolabe.getLocation().getLatitude()))
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawShadowSquare())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawUnequalHours())
                .append("\n").append("grestore");
        // Write Footer
        out.append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }

}
