package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

class Game{
    private static final int START_RANGE = 1;
    private static final int END_RANGE = 9;
    private static final int RESTART_GAME = 1;
    private static final int END_GAME = 2;
    private final int COUNT = 3;

    int ball, strike;

    void start(){
        System.out.println("숫자 야구 게임을 시작합니다.");
        boolean check = false;

        while (!check) {
            List<Integer> computerNumber = getComputerNumber();

            while (true) {
                List<Integer> userNumber = getUserNumber();

                calculateScore(userNumber, computerNumber);

                if (result()) {
                    System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
                    int choice = Integer.parseInt(Console.readLine());
                    if (choice == RESTART_GAME) {
                        break;
                    } else if (choice == END_GAME) {
                        check = true;
                        break;
                    }
                    else{
                        throw new IllegalArgumentException("게임 종료");
                    }
                }
            }
        }
    }

    private List<Integer> getComputerNumber(){
        List<Integer> computerNumber = new ArrayList<>();
        while (computerNumber.size() < COUNT) {
            int randomNumber = Randoms.pickNumberInRange(START_RANGE, END_RANGE);
            if (!computerNumber.contains(randomNumber)) {
                computerNumber.add(randomNumber);
            }
        }
        return computerNumber;
    }

    private List<Integer> getUserNumber() {
        List<Integer> userNumber = new ArrayList<>();
        System.out.print("숫자를 입력해주세요 : ");
        String input = Console.readLine();

        validateInput(input, userNumber);

        return userNumber;
    }

    private void validateInput(String input, List<Integer> userNumber) {
        if (input.length() != COUNT) {
            throw new IllegalArgumentException("3자리 숫자가 아닙니다.");
        }
        for (char digit : input.toCharArray()) {
            int num = digit - '0';
            if (num < START_RANGE || num > END_RANGE) {
                throw new IllegalArgumentException("1부터 9까지의 숫자를 입력해주세요.");
            }
            if (userNumber.contains(num)) {
                throw new IllegalArgumentException("중복된 숫자가 입력되었습니다.");
            }
            userNumber.add(num);
        }
    }

    void calculateScore(List<Integer>user, List<Integer>computer){
        ball = 0;
        strike = 0;

        for(int i = 0; i < COUNT; i++){
            for(int j = 0; j < COUNT; j++){
                if(Objects.equals(user.get(i), computer.get(j))){
                    if(i == j){
                        strike++;
                    } else{
                        ball++;
                    }
                }
            }
        }
    }

    boolean result(){
        if(this.ball == 0 && this.strike == 0){
            System.out.println("낫싱");
        }
        else if(ball > 0 && strike == 0){
            System.out.println(ball+"볼");
        }
        else if(ball == 0 && strike > 0){
            System.out.println(strike+"스트라이크");
            if(strike == COUNT){
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                return true;
            }
        }
        else if(ball > 0 && strike > 0){
            System.out.println(ball+"볼 "+strike+"스트라이크");
        }
        return false;
    }
}