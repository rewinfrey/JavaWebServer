package tests;

import org.junit.Test;
import server.Hello;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: rickwinfrey
 * Date: 2/5/13
 * Time: 2:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class HelloTest {
    String hello = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js\"></script>\n" +
            "    <style media=\"screen\" type=\"text/css\">\n" +
            "    body {font-family: FrescoSansPlusBold, Helvetica, Arial, sans-serif;background-color: #171717;}\n" +
            "    h1 {width: 435px;margin: 40px auto;font-size: 30px;color: whitesmoke;font-weight: 300;}\n" +
            "    h1 span {color: #2295D5;}\n" +
            "    h2 {width: 148px;margin: 40px auto 0 auto;font-size: 38px;color: whitesmoke;font-weight: 400;}\n" +
            "    h2 span {color: #2295D5; font-size: 40px;font-weight:500;}\n" +
            "    h3 {color: #2B2B2B;width: 176px;margin: 10px auto 0 auto;font-size: 18px;}\n" +
            "    h4 {color: #4C4C4C; }\n" +
            "    a {color: #4C4C4C; text-decoration: none; }\n" +
            "    #canvas {width: 741px;margin: auto;}\n" +
            "    #steps {height: 25px;}\n" +
            "    #predefined_routes { float: left; margin-left: 40px;}\n" +
            "    #predefined_routes h3 {width: 275px;}\n" +
            "    .explanation {width: 700px;margin: auto;}\n" +
            "    .text_right {text-align: right;margin-right: 88px;}\n" +
            "    .text_left {text-align: left;margin-left: 88px;}\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <h1>Welcome To <span>Rick's</span> HTTP Server</h1>\n" +
            "    <div id=\"predefined_routes\">\n" +
            "      <h3>Predefined Routes:</h3>\n" +
            "      <h4><a href=\"/\">/</a></h4>\n" +
            "      <h4><a href=\"/time\">/time</a></h4>\n" +
            "      <h4><a href=\"/form\">/form</a></h4>\n" +
            "      <h4><a href=\"/hello\">/hello</a></h4>\n" +
            "      <h3>File Types Currently Servable:</h3>\n" +
            "      <h4>.pdf</h4>\n" +
            "      <h4>.gif</h4>\n" +
            "      <h4>.html</h4>\n" +
            "      <h4>.png</h4>\n" +
            "      <h4>.jpg</h4>\n" +
            "      <h4>.txt</h4>\n" +
            "      <h4>.rb</h4>\n" +
            "      <h4>.java</h4>\n" +
            "    </div>\n" +
            "    <div id=\"canvas\">\n" +
            "      <canvas id=\"demo\" width=\"800\" height=\"400\" style=\"margin-top: 20px;\">Your browser does not support HTML5 :(</canvas>\n" +
            "      <h2><span>8th</span> Light</h2>\n" +
            "      <h3>Apprenticeship 2013</h3>\n" +
            "    </div>\n" +
            "  </body>\n" +
            "  <script language=\"javascript\" type=\"text/javascript\">\n" +
            "  (function() {\n" +
            "    var Animate, LogoSection, animate_back, get_logo_sections;\n" +
            "    \n" +
            "    CanvasRenderingContext2D.prototype.fillTextCircle = function(text, x, y, radius, startRotation, incrementor) {\n" +
            "      var index, letter, numRadsPerLetter, _i, _len;\n" +
            "      numRadsPerLetter = 2 * Math.PI / text.length;\n" +
            "      this.save();\n" +
            "      this.translate(x, y);\n" +
            "      this.rotate(startRotation + incrementor);\n" +
            "      for (index = _i = 0, _len = text.length; _i < _len; index = ++_i) {\n" +
            "        letter = text[index];\n" +
            "        this.save();\n" +
            "        this.rotate(index * numRadsPerLetter);\n" +
            "        this.fillText(letter, 0, -radius);\n" +
            "        this.restore();\n" +
            "      }\n" +
            "      return this.restore();\n" +
            "    };\n" +
            "\n" +
            "    LogoSection = (function() {\n" +
            "\n" +
            "      function LogoSection(fill, translation, rotation, start_point, height) {\n" +
            "        this.fill = fill;\n" +
            "        this.translation = translation;\n" +
            "        this.rotation = rotation;\n" +
            "        this.start_point = start_point;\n" +
            "        this.height = height;\n" +
            "      }\n" +
            "\n" +
            "      return LogoSection;\n" +
            "\n" +
            "    })();\n" +
            "\n" +
            "    Animate = (function() {\n" +
            "\n" +
            "      function Animate(logo_sections, frame_rate, canvas, fill) {\n" +
            "        this.logo_sections = logo_sections;\n" +
            "        this.frame_rate = frame_rate;\n" +
            "        this.canvas = canvas;\n" +
            "        this.fill = fill;\n" +
            "        this.ctx = document.getElementById(\"\" + this.canvas).getContext(\"2d\");\n" +
            "        this.height = parseInt($('#demo').css('height').replace(\"px\", \"\"));\n" +
            "        this.width = parseInt($('#demo').css('width').replace(\"px\", \"\"));\n" +
            "      }\n" +
            "\n" +
            "      Animate.prototype.draw_canvas = function() {\n" +
            "        this.ctx.fillStyle = this.fill;\n" +
            "        return this.ctx.fillRect(0, 0, this.width, this.height);\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.draw_logo = function() {\n" +
            "        var logo_section, _i, _len, _ref;\n" +
            "        this.ctx.save();\n" +
            "        _ref = this.logo_sections;\n" +
            "        for (_i = 0, _len = _ref.length; _i < _len; _i++) {\n" +
            "          logo_section = _ref[_i];\n" +
            "          this.set_fill(logo_section);\n" +
            "          this.translate(logo_section);\n" +
            "          this.rotate(logo_section);\n" +
            "          this.draw_triangle(logo_section);\n" +
            "        }\n" +
            "        this.draw_circle();\n" +
            "        return this.ctx.restore();\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.animate_logo = function() {\n" +
            "        var logo_section;\n" +
            "        this.ctx.clearRect(0, 0, this.width, this.height);\n" +
            "        this.draw_canvas();\n" +
            "        logo_section = this.logo_sections.shift();\n" +
            "        this.set_fill(logo_section);\n" +
            "        this.translate(logo_section);\n" +
            "        this.rotate(logo_section);\n" +
            "        this.move_left(logo_section);\n" +
            "        this.draw_circle();\n" +
            "        return this.replay();\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.move_left = function(logo_section) {\n" +
            "        logo_section.start_point.x -= 100;\n" +
            "        this.draw_triangle(logo_section);\n" +
            "        return logo_section.start_point.x += 100;\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.replay = function() {\n" +
            "        var _this = this;\n" +
            "        return window.setTimeout(function() {\n" +
            "          return _this.animate_logo();\n" +
            "        }, this.frame_rate);\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.set_fill = function(logo_section) {\n" +
            "        return this.ctx.fillStyle = logo_section.fill;\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.translate = function(logo_section) {\n" +
            "        return this.ctx.translate(logo_section.translation.x, logo_section.translation.y);\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.rotate = function(logo_section) {\n" +
            "        return this.ctx.rotate(logo_section.rotation);\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.draw_triangle = function(logo_section) {\n" +
            "        var height, t, x, y;\n" +
            "        height = logo_section.height;\n" +
            "        x = logo_section.start_point.x;\n" +
            "        y = logo_section.start_point.y;\n" +
            "        t = this;\n" +
            "        t.ctx.beginPath();\n" +
            "        t.ctx.moveTo(x, y);\n" +
            "        t.ctx.lineTo(x + 10, y - height);\n" +
            "        t.ctx.lineTo(x + 15, y - height);\n" +
            "        t.ctx.lineTo(x + 20, y);\n" +
            "        t.ctx.arcTo(x + 20, y, x + 10, y - 5, 10);\n" +
            "        t.ctx.arcTo(x + 10, y - 5, x, y, 20);\n" +
            "        t.ctx.fill();\n" +
            "        t.ctx.stroke();\n" +
            "        return t.ctx.closePath();\n" +
            "      };\n" +
            "\n" +
            "      Animate.prototype.draw_circle = function() {\n" +
            "        var t;\n" +
            "        t = this;\n" +
            "        t.ctx.beginPath();\n" +
            "        t.ctx.strokeStyle = \"#FFF\";\n" +
            "        t.ctx.arc(209, 228, 30, 0, 2 * Math.PI);\n" +
            "        t.ctx.lineWidth = \"15\";\n" +
            "        t.ctx.stroke();\n" +
            "        return t.ctx.closePath();\n" +
            "      };\n" +
            "\n" +
            "      return Animate;\n" +
            "\n" +
            "    })();\n" +
            "\n" +
            "    $(document).ready(function() {\n" +
            "      var animate, background_color, canvas_id, frame_rate, logo_sections;\n" +
            "      canvas_id = $('canvas').attr(\"id\");\n" +
            "      background_color = \"#171717\";\n" +
            "      frame_rate = 500;\n" +
            "      logo_sections = get_logo_sections();\n" +
            "      animate = new Animate(logo_sections, frame_rate, canvas_id, background_color);\n" +
            "      animate.draw_canvas();\n" +
            "      return animate.draw_logo();\n" +
            "    });\n" +
            "\n" +
            "    get_logo_sections = function() {\n" +
            "      var logo1, logo2, logo3, logo4, logo5, logo6, logo7, logo8, logos, origination;\n" +
            "      origination = new Object({\n" +
            "        x: 200,\n" +
            "        y: 200\n" +
            "      });\n" +
            "      logos = [];\n" +
            "      logo1 = new LogoSection('#2295D5', new Object({\n" +
            "        x: 400,\n" +
            "        y: -75\n" +
            "      }), 0.8, new Object({\n" +
            "        x: 200,\n" +
            "        y: 183\n" +
            "      }), 200);\n" +
            "      logo2 = new LogoSection('#FFF', new Object({\n" +
            "        x: -100,\n" +
            "        y: 225\n" +
            "      }), -0.82, origination, 140);\n" +
            "      logo3 = new LogoSection('#FFF', new Object({\n" +
            "        x: -100,\n" +
            "        y: 225\n" +
            "      }), -0.82, origination, 120);\n" +
            "      logo4 = new LogoSection('#FFF', new Object({\n" +
            "        x: -100,\n" +
            "        y: 205\n" +
            "      }), -0.75, origination, 80);\n" +
            "      logo5 = new LogoSection('#FFF', new Object({\n" +
            "        x: -100,\n" +
            "        y: 205\n" +
            "      }), -0.75, origination, 80);\n" +
            "      logo6 = new LogoSection('#FFF', new Object({\n" +
            "        x: -100,\n" +
            "        y: 225\n" +
            "      }), -0.82, origination, 80);\n" +
            "      logo7 = new LogoSection('#FFF', new Object({\n" +
            "        x: -100,\n" +
            "        y: 225\n" +
            "      }), -0.82, origination, 120);\n" +
            "      logo8 = new LogoSection('#FFF', new Object({\n" +
            "        x: -100,\n" +
            "        y: 205\n" +
            "      }), -0.75, origination, 140);\n" +
            "      return logos = [logo1, logo2, logo3, logo4, logo5, logo6, logo7, logo8];\n" +
            "    };\n" +
            "\n" +
            "    animate_back = function(string, ctx, frame_rate, incrementor) {\n" +
            "      var _this = this;\n" +
            "      return window.setTimeout(function() {\n" +
            "        ctx.clearRect(0, 0, 800, 800);\n" +
            "        if (incrementor === 365) {\n" +
            "          incrementor = 0;\n" +
            "        }\n" +
            "        ctx.fillTextCircle(string, 400, 400, 200, Math.PI / 2, incrementor += 1);\n" +
            "        return animate(string, ctx, frame_rate, incrementor);\n" +
            "      }, frame_rate);\n" +
            "    };\n" +
            "  }).call(this);\n" +
            "  </script>\n" +
            "</html>";
    @Test
    public void hello() {
       assertEquals(hello, Hello.helloHTML);
    }

    @Test
    public void Hello() {
       assertNotNull(new Hello());
    }
}
