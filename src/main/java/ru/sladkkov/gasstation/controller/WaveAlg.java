package ru.sladkkov.gasstation.controller;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WaveAlg {
    int width;
    int height;
    int wall = 99;
    int[][] map;
    List<Point> wave = new ArrayList<>();

    public WaveAlg(int width, int height) {
        this.width = width;
        this.height = height;
        map = new int[width + 2][height + 2];


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = -1;
            }
        }
        for (int i = 0; i < width; i++) {
            map[i][0] = wall;
            map[width - 1][i] = wall;
        }
        for (int i = 0; i < height; i++) {
            map[0][i] = wall;
            map[i][height - 1] = wall;
        }

    }


    ;

    public void block(int x, int y) {

        map[y][x] = wall;
    }

    public void unblock(int x, int y) {

        map[y][x] = wall;
    }

    public String findPath(int x, int y, int nx, int ny) {
        if (map[y][x] == wall || map[ny][nx] == wall) {
            return null;

        }

        int[][] cloneMap = clone(map);
        List<Point> oldWave = new ArrayList<>();
        oldWave.add(new Point(nx, ny));
        int nstep = 0;
        map[ny][nx] = nstep;

        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

        while (oldWave.size() > 0) {
            nstep++;
            wave.clear();
            for (Point i : oldWave) {
                for (int d = 0; d < 4; d++) {
                    nx = i.x + dx[d];
                    ny = i.y + dy[d];

                    if (map[ny][nx] == -1 || map[ny][nx] == 0) {
                        wave.add(new Point(nx, ny));
                        map[ny][nx] = nstep;
                    }

                }
            }
            oldWave = new ArrayList<>(wave);
        }

        boolean flag;
        wave.clear();
        wave.add(new Point(x, y));
        while (map[y][x] != 0) {
            flag = true;
            for (int d = 0; d < 4; d++) {
                nx = x + dx[d];
                ny = y + dy[d];
                if (map[y][x] - 1 == map[ny][nx]) {
                    x = nx;
                    y = ny;
                    wave.add(new Point(x, y));
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println("Выхода нет");
                break;
            }
        }

        map = cloneMap;
        List<String> race = new ArrayList<>();
        for (Point i : wave) {
            map[i.y][i.x] = 0;
            race.add((i.x - 1) + " " + (i.y - 1));
        }
        return race.toString();
    }

    static class Point {
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x;
        public int y;
    }

    public void waveOut() //
    {
        for (Point i : wave) {
            System.out.println("x = " + i.x + ", y = " + i.y);
        }
    }

    public void traceOut() {
        String m = null;
        System.out.print("   ");
        for (int i = 0; i < height; i++) {
            System.out.print(i > 9 ? i + " " : i + "  ");
        }
        System.out.println();
        for (int i = 0; i < width; i++) {
            m = i > 9 ? i + " " : i + "  ";
            for (int j = 0; j < height; j++) {
                m += map[i][j] > 9 || map[i][j] < 0 ? map[i][j] + " " : map[i][j] + "  ";
            }
            System.out.println(m);


        }


    }

    private int[][] clone(int[][] map) {
        int[][] cloneMap = new int[width + 2][height + 2];
        for (int i = 0; i < map.length; i++)
            System.arraycopy(map[i], 0, cloneMap[i], 0, map[i].length);
        return cloneMap;
    }
}