package com.wbw.iloveyou.util;


public class PhysicalTool {
        private static final float GRAVITY = 400.78033f;
        private static final float WASTAGE = 0.3f;
        private int height;
        private int width;
        private double velocity;
        private double x, y;
        private long startTime;
        private double t1;
        private double t2;
        private boolean doing;

        public void start() {
                startTime = System.currentTimeMillis();
                doing = true;
        }

        public void setParams(int h, int w) {
                height = h;
                width = w;

                t1 = Math.sqrt(2 * height * 1.0d / GRAVITY);
                t2 = Math.sqrt((1 - WASTAGE) * 2 * height * 1.0d / GRAVITY);
                velocity = width * 1.0d / (t1 + 2 * t2);
                
        }

        public void compute() {
                double used = (System.currentTimeMillis() - startTime) * 1.0d / 1000;
                x = velocity * used;
                if (0 <= used && used < t1) {
                        y = height - 0.5d * GRAVITY * used * used;
                } else if (t1 <= used && used < (t1 + t2)) {
                        double tmp = t1 + t2 - used;
                        y = (1 - WASTAGE) * height - 0.5d * GRAVITY * tmp * tmp;
                } else if ((t1 + t2) <= used && used < (t1 + 2 * t2)) {
                        double tmp = used - t1 - t2;
                        y = (1 - WASTAGE) * height - 0.5d * GRAVITY * tmp * tmp;
                } else {
                       
                        x = velocity * (t1 + 2 * t2);
                        y = 0;
                        doing = false;
                }
        }

        public double getX() {
                return x;
        }

        public double getY() {
                return y;
        }

        public double getMirrorY(int parentHeight, int bitHeight) {
                int half = parentHeight >> 1;
                double tmp = half + (half - y);
                tmp -= bitHeight;
                return tmp;
        }

        public boolean doing() {
                return doing;
        }

        public void cancel() {
                doing = false;
        }
}