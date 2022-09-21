package UI;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO{

    final private Scanner s = new Scanner(System.in);


    /**
     * @param msg
     */
    @Override
    public void print(String msg) {

        System.out.println(msg);

    }

    /**
     * @param msgPrompt
     * @return
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return s.nextLine();
    }

    /**
     * @param msgPrompt
     * @return
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(msgPrompt);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * @param msgPrompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     * @param msgPrompt
     * @return
     */
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     * @param msgPrompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     * @param msgPrompt
     * @return
     */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     * @param msgPrompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     * @param msgPrompt
     * @return
     */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     * @param msgPrompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    /**
     * @param prompt
     * @return
     */
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        BigDecimal bigDecimalInput = null;
        boolean invalidInput = true;

        while (invalidInput) {
            try {

                System.out.println(prompt);

                String stringInput = s.nextLine();
                bigDecimalInput = new BigDecimal(stringInput);
                invalidInput = false;
            } catch (NumberFormatException e) {

                this.print("Error Please enter a number.");

            }
        }
        return bigDecimalInput;
    }
}
