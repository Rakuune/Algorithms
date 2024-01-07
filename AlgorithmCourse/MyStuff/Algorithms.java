import java.util.Comparator;

public class Algorithms {

   private Algorithms() {
   }

   public static <T extends Comparable<T>> void insertionSort(T[] array) {
      int nameL = array.length;

      for (int i = 1; i < nameL; i++) {
         T currentName = array[i];
         int j = i - 1;

         if (currentName == null) {
            continue;
         }

         if (currentName.compareTo(array[j]) >= 0) {
            continue;
         }

         while (j >= 0 && currentName.compareTo(array[j]) < 0) {
            array[j + 1] = array[j];
            j--;
         }

         array[j + 1] = currentName;
      }

   }

   public static <T extends Comparable<T>> void insertionSort(T[] array, int fromIndex, int toIndex) {
      for (int i = fromIndex + 1; i <= toIndex; i++) {
         T currentName = array[i];
         int j = i - 1;

         if (currentName == null) {
            continue;
         }

         if (currentName.compareTo(array[j]) >= 0) {
            continue;
         }

         while (j >= fromIndex && currentName.compareTo(array[j]) < 0) {
            array[j + 1] = array[j];
            j--;
         }

         array[j + 1] = currentName;
      }
   }

   public static <T> void insertionSort(T[] array, Comparator<T> comparator) {
      int length = array.length;

      for (int i = 1; i < length; i++) {
         T currentElement = array[i];

         if (currentElement == null) {
            continue;
         }

         int j = i - 1;

         while (j >= 0) {
            T jElement = array[j];

            if (jElement == null) {
               j--;
               continue;
            }

            if (comparator.compare(currentElement, jElement) < 0) {
               array[j + 1] = jElement;
               j--;
            } else {
               break;
            }
         }

         array[j + 1] = currentElement;
      }
   }

   public static <T> void insertionSort(T[] array, int fromIndex, int toIndex, Comparator<T> comparator) {
      for (int i = fromIndex + 1; i <= toIndex; i++) {
         T currentElement = array[i];

         if (currentElement == null) {
            continue;
         }

         int j = i - 1;

         while (j >= fromIndex) {
            T jElement = array[j];

            if (jElement == null) {
               j--;
               continue;
            }

            if (comparator.compare(currentElement, jElement) < 0) {
               array[j + 1] = jElement;
               j--;
            } else {
               break;
            }
         }

         array[j + 1] = currentElement;
      }
   }

   public static <T> void reverse(T[] array) {
      int start = 0;
      int end = array.length - 1;

      while (start < end) {
         while (start < end && array[start] == null) {
            start++;
         }

         while (start < end && array[end] == null) {
            end--;
         }

         if (start < end) {
            T temp = array[start];
            array[start] = array[end];
            array[end] = temp;

            start++;
            end--;
         }
      }
   }

   public static <T> void reverse(T[] array, int fromIndex, int toIndex) {
      int start = fromIndex;
      int end = toIndex;

      while (start < end && array[start] == null) {
         start++;
      }

      while (start < end && array[end] == null) {
         end--;
      }
      while (start < end) {
         T temp = array[start];
         array[start] = array[end];
         array[end] = temp;

         start++;
         end--;
      }
      while (start < toIndex && array[start] == null) {
         start++;
      }
      while (end > fromIndex && array[end] == null) {
         end--;
      }
   }

   public static <T extends Comparable<T>> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex) {
      if (aValue != null) {
         while (fromIndex < toIndex) {
            int midIndex = fromIndex + (toIndex - fromIndex) / 2;
            int comparison = aValue.compareTo(fromArray[midIndex]);
            T midElement = fromArray[midIndex];
            if (midElement == null) {
               fromIndex = midIndex + 1;
               continue;
            }

            if (comparison == 0) {
               return midIndex;
            } else if (comparison < 0) {
               toIndex = midIndex;
            } else {
               fromIndex = midIndex + 1;
            }
         }
      }
      return -1;
   }

   public static <T> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex, Comparator<T> comparator) {
      if (aValue != null) {
         while (fromIndex < toIndex) {
            int midIndex = fromIndex + (toIndex - fromIndex) / 2;
            int comparison = comparator.compare(aValue, fromArray[midIndex]);
            T midElement = fromArray[midIndex];
            if (midElement == null) {
               fromIndex = midIndex + 1;
               continue;
            }

            if (comparison == 0) {
               return midIndex;
            } else if (comparison < 0) {
               toIndex = midIndex;
            } else {
               fromIndex = midIndex + 1;
            }
         }
      }
      return -1;
   }

   public static <E extends Comparable<E>> void fastSort(E[] array) {
      if (array == null || array.length < 2) {
         return;
      }
      E[] aux = (E[]) new Comparable[array.length];
      mergeSort(array, aux, 0, array.length);
   }

   private static <E extends Comparable<E>> void merge(E[] array, E[] aux, int low, int mid, int high) {
      for (int k = low; k < high; k++) {
         aux[k] = array[k];
      }

      int i = low, j = mid;
      for (int k = low; k < high; k++) {
         if (i >= mid)
            array[k] = aux[j++];
         else if (j >= high)
            array[k] = aux[i++];
         else if (aux[j].compareTo(aux[i]) < 0)
            array[k] = aux[j++];
         else
            array[k] = aux[i++];
      }
   }

   private static <E extends Comparable<E>> void mergeSort(E[] array, E[] aux, int low, int high) {
      if (high - low < 2) {
         return;
      }
      int mid = low + (high - low) / 2;
      mergeSort(array, aux, low, mid);
      mergeSort(array, aux, mid, high);
      merge(array, aux, low, mid, high);
   }

   public static <E> void fastSort(E[] array, Comparator<E> comparator) {
      if (array == null || array.length < 2) {
         return;
      }
      E[] aux = (E[]) new Object[array.length];
      mergeSort(array, aux, 0, array.length, comparator);
   }

   private static <E> void mergeSort(E[] array, E[] aux, int low, int high, Comparator<E> comp) {
      if (high - low < 2) {
         return;
      }
      int mid = low + (high - low) / 2;
      mergeSort(array, aux, low, mid, comp);
      mergeSort(array, aux, mid, high, comp);
      merge(array, aux, low, mid, high, comp);
   }

   private static <E> void merge(E[] array, E[] aux, int low, int mid, int high, Comparator<E> comp) {
      for (int k = low; k < high; k++) {
         aux[k] = array[k];
      }

      int i = low, j = mid;
      for (int k = low; k < high; k++) {
         if (i >= mid)
            array[k] = aux[j++];
         else if (j >= high)
            array[k] = aux[i++];
         else if (comp.compare(aux[j], aux[i]) < 0)
            array[k] = aux[j++];
         else
            array[k] = aux[i++];
      }
   }

   public static <E> void fastSort(E[] array, int fromIndex, int toIndex, Comparator<E> comparator) {

      if (array == null || fromIndex < 0 || toIndex > array.length || toIndex <= fromIndex) {
         throw new IllegalArgumentException("Invalid range: " + fromIndex + " to " + toIndex);
      }

      E[] subArray = (E[]) new Object[toIndex - fromIndex];
      for (int i = fromIndex; i < toIndex; i++) {
         subArray[i - fromIndex] = array[i];
      }

      mergeSort(subArray, 0, subArray.length, comparator);

      for (int i = 0; i < subArray.length; i++) {
         array[fromIndex + i] = subArray[i];
      }
   }

   private static <E> void mergeSort(E[] array, int low, int high, Comparator<E> comp) {
      if (high - low < 2) {
         return;
      }

      int mid = low + (high - low) / 2;
      mergeSort(array, low, mid, comp);
      mergeSort(array, mid, high, comp);

      merge(array, low, mid, high, comp);
   }

   private static <E> void merge(E[] array, int low, int mid, int high, Comparator<E> comp) {
      E[] temp = (E[]) new Object[high - low];
      int i = low, j = mid, k = 0;

      while (i < mid && j < high) {
         if (comp.compare(array[i], array[j]) <= 0) {
            temp[k++] = array[i++];
         } else {
            temp[k++] = array[j++];
         }
      }

      while (i < mid) {
         temp[k++] = array[i++];
      }

      while (j < high) {
         temp[k++] = array[j++];
      }

      for (i = low, k = 0; i < high; i++, k++) {
         array[i] = temp[k];
      }
   }

}
