package org.vaccom.vcmgt.controler;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vaccom.vcmgt.security.impl.RandomString;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/v1/captcha")
public class CaptchaController {

	@RequestMapping(value = "/image", method = RequestMethod.GET, produces = "application/octet-stream")
	public ResponseEntity<?> getDSCoSoYTe(HttpServletRequest request, HttpServletResponse response) {

		File destDir = new File("../captchas");

		if (!destDir.exists()) {
			destDir.mkdir();
		}

		File file = null;

		try {

			file = new File(destDir + "/" + System.currentTimeMillis() + ".jpeg");

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=captcha.jpg");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			RandomString random = new RandomString(6);

			String captcha = random.nextString();

			int width = 200, height = 60;

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.OPAQUE);
			//Graphics2D g = bufferedImage.createGraphics();
			//GradientPaint primary = new GradientPaint(0f, 0f, Color.WHITE, 200f, 0f, Color.GRAY);
			//GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0), 0f, 200f, new Color(0, 0, 0, 255));
			//g.setPaint(primary);
			//g.fillRect(0, 0, 100, 35);
			//g.setPaint(shade);
			//g.fillRect(0, 0, width, height);
			//g.drawString(captcha, 20, 25);
			//g.setFont(new Font("Arial", Font.BOLD, 22));
			
			//g.setColor(new Color(169, 169, 169));
			//g.fillRect(0, 0, width, height);
			//g.setColor(new Color(255, 255, 255));
			
			Graphics graphics = bufferedImage.createGraphics();
			graphics.setFont(new Font("Arial", Font.BOLD, 40));
			graphics.setColor(new Color(169, 169, 169));
			graphics.fillRect(0, 0, width, height);
			graphics.setColor(new Color(255, 255, 255));
			graphics.drawString(captcha, 30, 30);

			HttpSession session = request.getSession(true);

			session.setAttribute("captcha", captcha);

			ImageIO.write(bufferedImage, "jpeg", file);

			if (file != null && file.exists()) {
				InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			_log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	private final Logger _log = LoggerFactory.getLogger(CaptchaController.class);
}
