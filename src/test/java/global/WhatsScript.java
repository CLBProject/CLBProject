package global;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class WhatsScript {

	public static void main(String[] args) throws AWTException {
		Robot robot = new Robot();
		
		while(true) {
			type("Lixo\n",robot);
			robot.delay(5);
		}
	}

	private static void leftClick(Robot robot)
	{
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(200);
	}

	private static void type(String s, Robot robot)
	{
		byte[] bytes = s.getBytes();
		for (byte b : bytes)
		{
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code > 96 && code < 123) code = code - 32;
			robot.delay(6);
			robot.keyPress(code);
			robot.keyRelease(code);
		}
	}
}
