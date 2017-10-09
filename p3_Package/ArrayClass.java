package p3_Package;

import java.util.Random;

/**
 * Created by Adam Goins.
 * CS-249 Assignment 3: p3_Package.ArrayClass
 *
 * @author Adam Goins
 *
 * The p3_Package.ArrayClass will act as a modified Array to demonstrate understanding of the datastructure
 */
public class ArrayClass
{

    private int arrayCapacity;
    private int arraySize;
    private final static int DEFAULT_CAPACITY = 10;
    static final int FAILED_ACCESS = -999999;
    private int[] localArray;

    /**
     * Default Constructor, initializes array to default capacity (10)
     */
    public ArrayClass()
    {
        this.arrayCapacity = DEFAULT_CAPACITY;
        this.arraySize = 0;
        this.localArray = new int[arrayCapacity];
    }

    /**
     * Copy constructor, initializes array to size and capacity of copied array,
     * then copies only elements up to the given size
     *
     * @param copied The p3_Package.ArrayClass that will be copied
     */
    public ArrayClass(ArrayClass copied)
    {
        this.arraySize = copied.getCurrentSize();
        this.arrayCapacity = copied.getCurrentCapacity();
        this.localArray = new int[arrayCapacity];

        int indexIterator;

        for (indexIterator = 0; indexIterator < arraySize; indexIterator++)
        {
            try
            {
                localArray[indexIterator] = copied.accessItemAt(indexIterator);
            }
            catch (Exception caughtException)
            {
                caughtException.printStackTrace();
            }
        }
    }

    /**
     * Initializing constructor, initializes array to specified capacity.
     *
     * @param arrayCapacity The capacity of the array
     */
    public ArrayClass(int arrayCapacity)
    {

        if (arrayCapacity < 0)
        {
            return;
        }

        this.arrayCapacity = arrayCapacity;
        this.arraySize = 0;
        this.localArray = new int[arrayCapacity];
    }

    /**
     * Initializing constructor, initializes array to specified capacity, size to specified value,
     * then fills all elements with specified size value
     *
     * @param capacity  The capacity of the array
     * @param size      The size of the array
     * @param fillValue The value to fill the array with
     */
    public ArrayClass(int capacity, int size, int fillValue)
    {
        if (capacity < 0 || size < 0 || size > capacity)
        {
            return;
        }

        this.arrayCapacity = capacity;
        this.arraySize = size;
        this.localArray = new int[arrayCapacity];

        int indexIterator;

        for (indexIterator = 0; indexIterator < this.arrayCapacity; indexIterator++)
        {
            localArray[indexIterator] = fillValue;
        }
    }


    public boolean loadUniqueRandoms(int numRands, int lowLimit, int highLimit)
    {
      if (lowLimit > highLimit)
      {
        return false;
      }

      if (numRands > getCurrentCapacity())
      {
        boolean resizeResult = resize(numRands);
        if (!resizeResult)
        {
          return false;
        }
      }

      if (numRands > getCurrentSize())
      {
        this.arraySize = numRands;
      }

      int randCounter;
      for (randCounter = 0; randCounter < numRands; randCounter++)
      {
        this.localArray[randCounter] = getUniqueRandom(lowLimit, highLimit);
      }
      return true;
    }

    /**
     * Tests for value found in object array; returns true if value within array, false otherwise
     *
     * @param testVal Value to test
     *
     * @return True or False
     */
    public boolean isInArray(int testVal)
    {
      boolean isIn = false;
      for (int value : this.localArray)
      {
        if (value == testVal)
        {
          isIn = true;
        }
      }
      return isIn;
    }

    /**
     * Loads a specified number of unique random numbers in object
     *
     * @param min Min value the random can have
     * @param max Max value the random can have
     *
     * @return Unique random in range
     */
    private int getUniqueRandom(int min, int max)
    {
      int random = getRandom(min, max);
      while ( isInArray(random) )
      {
          random = getRandom(min, max);
      }
      return random;
    }

    private int getRandom(int min, int max)
    {
      return new Random().nextInt((max - min) + 1) + min;
    }



    /**
     * Accesses item in array at specified index if index within array size bounds
     *
     * @param accessIndex The index to access the p3_Package.ArrayClass at
     * @return The item at the specified index
     */
    int accessItemAt(int accessIndex)
    {
        int indexBounds = getCurrentSize();

        if (accessIndex >= 0 && accessIndex < indexBounds)
        {
            return localArray[accessIndex];
        }
        return FAILED_ACCESS;
    }

    /**
     * Appends item to end of array, if array is not full, e.g., no more values can be added
     *
     * @param newValue The value to be appended to the p3_Package.ArrayClass
     * @return True or false if the newValue was successfully added to the p3_Package.ArrayClass or not.
     */
    boolean appendItem(int newValue)
    {
        int currentSize = getCurrentSize();
        int currentCapacity = getCurrentCapacity();

        if (currentSize == currentCapacity)
        {
            return false;
        }
        else
        {
            localArray[currentSize] = newValue;
            this.arraySize++;
            return true;
        }
    }

    /**
     * Data sorted using quick sort algorithm
     */
	void runQuickSort()
    {
        int leftIndex = 0;
        int rightIndex = getCurrentSize();

		runQuickSortHelper(leftIndex, rightIndex);
	}

	void runQuickSortHelper(int leftIndex, int rightIndex)
    {

        int wall = leftIndex;
        int lastItemIndex = rightIndex - 1;
        int pivot = accessItemAt(lastItemIndex);
        int currentElement;
        int currentElementIterator;

        System.out.println("Left Index: " + leftIndex);
        System.out.println("Right Index: " + rightIndex);

        if (leftIndex - rightIndex >= 0)
        {
            return;
        }

        for (currentElementIterator = wall; currentElementIterator < rightIndex; currentElementIterator++)
        {

            System.out.println("\n------------------------------------------");
            currentElement = accessItemAt(currentElementIterator);

            System.out.println("\nArray: " + this);
            System.out.println("Wall: " + wall);
            System.out.println("Pivot: " + pivot);
            System.out.println("Current element iterator: " + currentElementIterator);
            System.out.println("Current element: " + currentElement);
            if (currentElement >= pivot)
            {
                System.out.println("Current element is GREATER");
                continue;
            }
            else
                {
                System.out.println("Current element is SMALLER");
                System.out.println("Swapping " + this.localArray[currentElementIterator] + " with " + this.localArray[wall]);
                swapPositions(currentElementIterator, wall);
                System.out.println("\nNew Array: " + this + "\n\n");
                wall++;
            }
        }

        System.out.println("Inserting " + pivot + " into index " + wall);
        removeItemAt(lastItemIndex);
        System.out.println("Array after removal: " + this);
        insertItemAt(wall, pivot);
        System.out.println("Array after insert: " + this + "\n\n");
        wall++;
        runQuickSortHelper(leftIndex, wall - 1);

        System.out.println("Sorting right half....");
        runQuickSortHelper(wall, rightIndex);
        System.out.println("\n------------------------------------------");
        return;
	}

    /**
     * Data sorted using merge sort algorithm
     */
    void runMergeSort()
    {
        int leftIndex = 0;
        int rightIndex = getCurrentSize();

        runMergeSortHelper(this.localArray, leftIndex, rightIndex);
    }

    void runMergeSortHelper(int[] array, int leftIndex, int rightIndex)
    {

        if (leftIndex == 0 && rightIndex == 0)
        {
            return;
        }

        System.out.println("Left Index: " + leftIndex);
        System.out.println("Right Index: " + rightIndex);
        int middleIndex = (rightIndex - leftIndex) / 2 + leftIndex;

        System.out.println("Middle Index: " + middleIndex);
        int sizeLeft = middleIndex - leftIndex;
        int sizeRight = rightIndex - middleIndex;

        int[] leftArray;
        int[] rightArray;

        System.out.println("Sub Array");
        printArray(array);

        leftArray = copyArray(sizeLeft, leftIndex, middleIndex);
        System.out.println();
        System.out.println("Contents of Temp Left");
        printArray(leftArray);
        System.out.println();


        rightArray = copyArray(sizeRight, middleIndex, rightIndex);
        System.out.println("Contents of Temp right");
        printArray(rightArray);
        System.out.println();

        if (sizeLeft > 1) runMergeSortHelper(leftArray, leftIndex, middleIndex);
        if (sizeRight > 1) runMergeSortHelper(rightArray, middleIndex + 1, rightIndex);

        merge(leftIndex, rightIndex);

        System.out.println("\n------------------------------------------");
        return;
    }

    void merge(int leftIndex, int rightIndex)
    {
        int leftOffset = 0;
        int rightOffset = 0;

        int middleIndex = (rightIndex - leftIndex) + leftIndex;

        int sizeLeft = middleIndex - leftIndex - 1;
        int sizeRight = rightIndex - middleIndex + 1;

        int trueIndex = leftIndex;

        System.out.println("Size left: " + sizeLeft);
        System.out.println("Left offset: " + leftOffset);

        System.out.println();

        System.out.println("Size right: " + sizeRight);
        System.out.println("Right offset: " + rightOffset);


        while (leftOffset < sizeLeft && rightOffset < sizeRight)
        {
            System.out.println("merging..");
             int itemLeft = accessItemAt(leftIndex + leftOffset);
            System.out.println("Left: " + itemLeft);

             int itemRight = accessItemAt(rightIndex + rightOffset);
            System.out.println("Right: " + itemRight);
             if (itemLeft <= itemRight)
             {
                 this.localArray[trueIndex] = accessItemAt(leftOffset);
                 leftOffset++;
             }
             else
             {
                 this.localArray[trueIndex] = accessItemAt(rightOffset);
                 rightOffset++;
             }
             trueIndex++;
        }

        while (leftOffset < sizeLeft)
        {
             this.localArray[trueIndex] = accessItemAt(leftIndex + leftOffset);
             leftOffset++;
             trueIndex++;
        }

        while (rightIndex < sizeRight)
        {
             this.localArray[trueIndex] = accessItemAt(rightIndex + rightOffset);
             rightOffset++;
             trueIndex++;
        }
    }

	void swapPositions(int currentIndex, int wall)
	{
		int temp = this.localArray[currentIndex];

		this.localArray[currentIndex] = this.localArray[wall];
		this.localArray[wall] = temp;
    }


	int[] copyArray(int size, int startIndex, int endIndex)
	{
		int indexIterator;
        int currentItemIndex;
        int currentItem;
        int[] temp = new int[size];
		for (indexIterator = 0; indexIterator < endIndex - startIndex; indexIterator++)
        {
            currentItemIndex = startIndex + indexIterator;
            currentItem = accessItemAt(currentItemIndex);
            temp[indexIterator] = currentItem;
        }
        return temp;
    }

	void printArray(int[] array){
        for (int item : array) {
            System.out.print(item + ",\t");
        }
        System.out.println();
    }
    /**
     * Clears array of all valid values by setting array size to zero, values remain in array but are not accessible
     */
    void clear()
    {
        this.arraySize = 0;
    }

    /**
     * Description: Gets current capacity of array
     *
     * @return The capacity of the p3_Package.ArrayClass
     */
    int getCurrentCapacity()
    {
        return this.arrayCapacity;
    }

    /**
     * Description: Gets current size of array
     *
     * @return The current size of the array.
     */
    int getCurrentSize()
    {
        return this.arraySize;
    }


    /**
     * Description: Inserts item to array at specified index if array is not full, e.g., no more values can be added
     *
     * @param insertIndex The index to insert the value at
     * @param newValue    The value to insert into the p3_Package.ArrayClass
     *
     * @return True or false if the item was successfully inserted or not
     */
    boolean insertItemAt(int insertIndex, int newValue)
    {

        int indexLowerBound = 0;
        int indexUpperBound = getCurrentCapacity() - 1;
        if (isFull() || insertIndex < indexLowerBound || insertIndex > indexUpperBound)
        {
            return false;
        }

        int indexIterator;
        int startIndex = getCurrentCapacity() - 2;
        for (indexIterator = startIndex; indexIterator >= insertIndex; indexIterator--)
        {
            localArray[indexIterator + 1] = localArray[indexIterator];
        }

        localArray[insertIndex] = newValue;
        this.arraySize++;

        return true;
    }


    /**
     * Tests for size of array equal to zero, no valid values stored in array
     *
     * @return Returns true or false if the p3_Package.ArrayClass is empty or not
     */
    boolean isEmpty()
    {
        return getCurrentSize() == 0;
    }

    /**
     * Tests for size of array equal to capacity, no more values can be added
     *
     * @return True of false if the p3_Package.ArrayClass is full or not
     */
    boolean isFull()
    {
        return getCurrentSize() == getCurrentCapacity();
    }

    /**
     * Description: Removes item from array at specified index if index within array size bounds
     *
     * @param removeIndex The index to remove the item at
     *
     * @return The value of the array at that index
     */
    int removeItemAt(int removeIndex)
    {
        if (removeIndex < 0 || removeIndex >= getCurrentSize())
        {
            return FAILED_ACCESS;
        }
        else
        {
            int valueToRemove = accessItemAt(removeIndex);
            int indexIterator;
            int exitCondition = getCurrentCapacity() - 1;

            for (indexIterator = removeIndex; indexIterator < exitCondition; indexIterator++)
            {
                int nextIndex = indexIterator + 1;
                localArray[indexIterator] = localArray[nextIndex];
            }
            this.arraySize--;
            return valueToRemove;
        }
    }

    /**
     * Description: Resets array capacity, copies current size and current size number of elements
     *
     * @param newCapacity The new capacity that the p3_Package.ArrayClass should be
     *
     * @return True of False if the capacity was a valid capacity or not.
     */
    boolean resize(int newCapacity)
    {
        if (newCapacity <= getCurrentSize())
        {
            return false;
        }
        else
        {
            this.arrayCapacity = newCapacity;

            ArrayClass tempArray = new ArrayClass(this);
            this.localArray = new int[getCurrentCapacity()];

            int indexIterator;
            int exitCondition = getCurrentSize();

            for (indexIterator = 0; indexIterator < exitCondition; indexIterator++)
            {
                localArray[indexIterator] = tempArray.accessItemAt(indexIterator);
            }
            return true;
        }
    }

    /**
     * Displays the contents of the array as a string
     *
     * @return A string representation of the data contained in the p3_Package.ArrayClass
     */
    @Override
    public String toString()
    {
        if (getCurrentSize() == 0)
        {
            return "";
        }

        String returnString = "\t";
        int indexIterator;
        int exitCondition = getCurrentSize() - 1;

        for (indexIterator = 0; indexIterator < exitCondition; indexIterator++)
        {
            int arrayItem = accessItemAt(indexIterator);
            returnString += Integer.toString(arrayItem) + ",\t";
        }

        int lastItem = accessItemAt(exitCondition) ;
        returnString += Integer.toString(lastItem);

        return returnString;
    }

    private static class Tester {

        public static void main(String[] args){
                ArrayClass array = new ArrayClass();
                array.loadUniqueRandoms(10, 1, 10);
                System.out.println("Loaded Array");
                System.out.println(array);
                array.runQuickSort();
                System.out.println("\n\nQUICK SORTED ARRAY\n" + array);

                System.out.println("\n\n\n=====");

                array = new ArrayClass();
                array.loadUniqueRandoms(10, 1, 10);
                System.out.println("Loaded Array");
                System.out.println(array);
                array.runMergeSort();
                System.out.println("\n\nMERGE SORTED ARRAY\n" + array);
            }
    }
}
