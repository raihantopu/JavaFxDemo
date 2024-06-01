package com.mtr.service;

import static com.mtr.utility.Utility.currentLineColor;
import static com.mtr.utility.Utility.iIndexColor;
import static com.mtr.utility.Utility.sleep;
import static com.mtr.utility.Utility.sortedColor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

@Service
public class SortService {

	private final DrawingService drawingService;
	
	public SortService(DrawingService drawingService) {
		this.drawingService = drawingService;
	}
	//Bubble sort
	public List<Line> bubbleSort(List<Line> lines, HBox hbox, int time) {
        int n = lines.size();
        for (int i = 0; i < n - 1; i++) {
            lines.get(i).setStroke(iIndexColor);
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
            for (int j = 0; j < n - 1 - i; j++) {
                lines.get(j).setStroke(currentLineColor);
                if (Integer.parseInt(lines.get(j).getId()) > Integer.parseInt(lines.get(j + 1).getId())) {
                    Line temp = lines.get(j);
                    lines.set(j, lines.get(j + 1));
                    lines.get(j + 1).setStroke(sortedColor);
                    lines.set(j + 1, temp);
                    Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
                    sleep(time);
                }
            }
        }
        this.drawingService.changeToSortedColor(lines, hbox);
        return lines;
    }
	
	//Selection sort
	public List<Line> selectionSort(List<Line> lines, HBox hbox, int time) {
        int n = lines.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            lines.get(minIndex).setStroke(iIndexColor);
            for (int j = i + 1; j < n; j++) {
                lines.get(j).setStroke(currentLineColor);
                if (Integer.parseInt(lines.get(j).getId()) < Integer.parseInt(lines.get(minIndex).getId())) {
                    minIndex = j;
                }
                Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
                sleep(time);
            }
            Line temp = lines.get(i);
            lines.set(i, lines.get(minIndex));
            lines.get(minIndex).setStroke(sortedColor);
            lines.set(minIndex, temp);
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }
        this.drawingService.changeToSortedColor(lines, hbox);
        return lines;
    }
	//Insertion sort
    public List<Line> insertionSort(List<Line> lines, HBox hbox, int time) {
        int n = lines.size();
        for (int i = 1; i < n; i++) {
            Line line = lines.get(i);
            int j = i - 1;
            while (j >= 0 && Integer.parseInt(lines.get(j).getId()) > Integer.parseInt(line.getId())) {
                lines.set(j + 1, lines.get(j));
                lines.get(j).setStroke(currentLineColor);
                Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
                sleep(time);
                j--;
            }
            lines.set(j + 1, line);
            lines.get(j + 1).setStroke(sortedColor);
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }
        this.drawingService.changeToSortedColor(lines, hbox);
        return lines;
    }
    //Quick sort
    public List<Line> quickSort(List<Line> lines, HBox hbox, int time) {
        quickSortHelper(lines, 0, lines.size() - 1, hbox, time);
        this.drawingService.changeToSortedColor(lines, hbox);
        return lines;
    }

    private void quickSortHelper(List<Line> lines, int low, int high, HBox hbox, int time) {
        if (low < high) {
            int pi = partition(lines, low, high, hbox, time);
            quickSortHelper(lines, low, pi - 1, hbox, time);
            quickSortHelper(lines, pi + 1, high, hbox, time);
        }
    }

    private int partition(List<Line> lines, int low, int high, HBox hbox, int time) {
        Line pivot = lines.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (Integer.parseInt(lines.get(j).getId()) < Integer.parseInt(pivot.getId())) {
                i++;
                Line temp = lines.get(i);
                lines.set(i, lines.get(j));
                lines.get(j).setStroke(currentLineColor);
                lines.set(j, temp);
                Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
                sleep(time);
            }
        }
        Line temp = lines.get(i + 1);
        lines.set(i + 1, lines.get(high));
        lines.get(high).setStroke(sortedColor);
        lines.set(high, temp);
        Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
        sleep(time);
        return i + 1;
    }
    //Merge sort
    public List<Line> mergeSort(List<Line> lines, HBox hbox, int time) {
        mergeSortHelper(lines, 0, lines.size() - 1, hbox, time);
        this.drawingService.changeToSortedColor(lines, hbox);
        return lines;
    }

    private void mergeSortHelper(List<Line> lines, int left, int right, HBox hbox, int time) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortHelper(lines, left, mid, hbox, time);
            mergeSortHelper(lines, mid + 1, right, hbox, time);
            merge(lines, left, mid, right, hbox, time);
        }
    }

    private void merge(List<Line> lines, int left, int mid, int right, HBox hbox, int time) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Line> L = new ArrayList<>(lines.subList(left, mid + 1));
        List<Line> R = new ArrayList<>(lines.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (Integer.parseInt(L.get(i).getId()) <= Integer.parseInt(R.get(j).getId())) {
                lines.set(k, L.get(i));
                L.get(i).setStroke(currentLineColor);
                i++;
            } else {
                lines.set(k, R.get(j));
                R.get(j).setStroke(currentLineColor);
                j++;
            }
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
            k++;
        }

        while (i < n1) {
            lines.set(k, L.get(i));
            L.get(i).setStroke(currentLineColor);
            i++;
            k++;
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }

        while (j < n2) {
            lines.set(k, R.get(j));
            R.get(j).setStroke(currentLineColor);
            j++;
            k++;
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }
    }


}
