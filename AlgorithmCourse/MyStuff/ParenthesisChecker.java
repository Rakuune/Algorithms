import oy.interact.tira.util.StackInterface;

public class ParenthesisChecker {

   private ParenthesisChecker() {
   }

   public static int checkParentheses(StackInterface<Character> stack, String fromString) throws ParenthesesException {
      int lineNumber = 1;
      int columnNumber = 1;
      int parenthesesCount = 0;
      boolean insideQuotes = false;

      for (char c : fromString.toCharArray()) {
         if (c == '\n') {
            lineNumber++;
            columnNumber = 0;
         }

         if (c == '"') {
            insideQuotes = !insideQuotes; 
         }

         if (!insideQuotes) {
            if (c == '(' || c == '[' || c == '{') {
               try {
                  stack.push(c);
                  parenthesesCount++;
               } catch (Exception e) {
                  throw new ParenthesesException("Stack allocation failed", lineNumber, columnNumber,
                        ParenthesesException.STACK_FAILURE);
               }
            } else if (c == ')' || c == ']' || c == '}') {
               if (stack.isEmpty()) {
                  throw new ParenthesesException("Too many closing parentheses", lineNumber, columnNumber,
                        ParenthesesException.TOO_MANY_CLOSING_PARENTHESES);
               }

               char openingParenthesis = stack.pop();
               parenthesesCount++;

               if ((c == ')' && openingParenthesis != '(') ||
                     (c == ']' && openingParenthesis != '[') ||
                     (c == '}' && openingParenthesis != '{')) {
                  throw new ParenthesesException("Wrong kind of parenthesis", lineNumber, columnNumber,
                        ParenthesesException.PARENTHESES_IN_WRONG_ORDER);
               }
            }
         }

         columnNumber++;
      }

      if (!stack.isEmpty()) {
         throw new ParenthesesException("Too few closing parentheses", lineNumber, columnNumber - 1,
               ParenthesesException.TOO_MANY_OPENING_PARENTHESES);
      }

      return parenthesesCount;
   }
}
