## INFO ##

Benchamark date: 2015-03-05

Server & Client on same machine: MacBook Pro (Retina, 13-inch, Early 2013) - OS X Yosemite 10.10.2 (14C109)
2.6 GHz Intel Core i5
8 GB 1600 MHz DDR3


## Snowflake ##

Concurrency = 4
```
[Snowflake]:    Completed 320000 ops in 14.588 secs (~21935.838 ops/sec), op time: 0.00/106.00/0.18/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 13.251 secs (~24149.121 ops/sec), op time: 0.00/17.00/0.16/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.619 secs (~25358.586 ops/sec), op time: 0.00/25.00/0.16/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.258 secs (~26105.401 ops/sec), op time: 0.00/5.00/0.15/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.438 secs (~25727.609 ops/sec), op time: 0.00/9.00/0.15/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.997 secs (~24621.066 ops/sec), op time: 0.00/17.00/0.16/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 13.264 secs (~24125.452 ops/sec), op time: 0.00/23.00/0.16/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.124 secs (~26393.929 ops/sec), op time: 0.00/5.00/0.15/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.158 secs (~26320.118 ops/sec), op time: 0.00/5.00/0.15/0.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.070 secs (~26512.013 ops/sec), op time: 0.00/16.00/0.15/0.00/1.00 (ms)
```

Concurrency = 16
```
[Snowflake]:    Completed 320000 ops in 11.439 secs (~27974.473 ops/sec), op time: 0.00/138.00/0.56/1.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 10.039 secs (~31875.685 ops/sec), op time: 0.00/125.00/0.49/1.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.787 secs (~32696.434 ops/sec), op time: 0.00/117.00/0.48/1.00/2.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.840 secs (~32520.325 ops/sec), op time: 0.00/110.00/0.48/1.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.930 secs (~32225.579 ops/sec), op time: 0.00/84.00/0.49/1.00/2.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.820 secs (~32586.558 ops/sec), op time: 0.00/111.00/0.48/1.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.873 secs (~32411.628 ops/sec), op time: 0.00/102.00/0.49/1.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 12.122 secs (~26398.284 ops/sec), op time: 0.00/151.00/0.60/1.00/2.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.993 secs (~32022.416 ops/sec), op time: 0.00/104.00/0.49/1.00/1.00 (ms)
[Snowflake]:    Completed 320000 ops in 10.056 secs (~31821.798 ops/sec), op time: 0.00/124.00/0.49/1.00/2.00 (ms)
```

Concurrency = 64
```
[Snowflake]:    Completed 320000 ops in 11.665 secs (~27432.490 ops/sec), op time: 0.00/252.00/2.25/1.00/6.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.144 secs (~34995.626 ops/sec), op time: 0.00/248.00/1.72/1.00/5.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.117 secs (~35099.265 ops/sec), op time: 0.00/201.00/1.73/1.00/5.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.348 secs (~34231.921 ops/sec), op time: 0.00/261.00/1.79/1.00/4.00 (ms)
[Snowflake]:    Completed 320000 ops in 8.644 secs (~37019.898 ops/sec), op time: 0.00/143.00/1.66/1.00/5.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.008 secs (~35523.979 ops/sec), op time: 0.00/179.00/1.72/1.00/5.00 (ms)
[Snowflake]:    Completed 320000 ops in 8.899 secs (~35959.097 ops/sec), op time: 0.00/169.00/1.71/1.00/5.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.082 secs (~35234.530 ops/sec), op time: 0.00/145.00/1.75/1.00/5.00 (ms)
[Snowflake]:    Completed 320000 ops in 8.679 secs (~36870.607 ops/sec), op time: 0.00/127.00/1.66/1.00/4.55 (ms)
[Snowflake]:    Completed 320000 ops in 8.780 secs (~36446.469 ops/sec), op time: 0.00/140.00/1.66/1.00/6.00 (ms)
```

Concurrency = 256
```
[Snowflake]:    Completed 320000 ops in 15.958 secs (~20052.638 ops/sec), op time: 0.00/411.00/12.38/7.00/48.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.003 secs (~35543.708 ops/sec), op time: 0.00/258.00/6.83/6.00/21.55 (ms)
[Snowflake]:    Completed 320000 ops in 9.352 secs (~34217.280 ops/sec), op time: 0.00/226.00/7.00/6.00/31.00 (ms)
[Snowflake]:    Completed 320000 ops in 9.155 secs (~34953.577 ops/sec), op time: 0.00/237.00/6.98/5.00/30.10 (ms)
[Snowflake]:    Completed 320000 ops in 8.847 secs (~36170.453 ops/sec), op time: 0.00/255.00/6.72/5.00/23.55 (ms)
[Snowflake]:    Completed 320000 ops in 9.203 secs (~34771.270 ops/sec), op time: 0.00/271.00/6.95/6.00/18.55 (ms)
[Snowflake]:    Completed 320000 ops in 9.691 secs (~33020.328 ops/sec), op time: 0.00/258.00/7.06/5.00/21.55 (ms)
[Snowflake]:    Completed 320000 ops in 8.975 secs (~35654.596 ops/sec), op time: 0.00/223.00/6.79/5.00/28.00 (ms)
[Snowflake]:    Completed 320000 ops in 8.968 secs (~35682.426 ops/sec), op time: 0.00/257.00/6.64/6.00/25.55 (ms)
[Snowflake]:    Completed 320000 ops in 8.871 secs (~36072.596 ops/sec), op time: 0.00/237.00/6.84/5.00/34.10 (ms)
```


## Redis ##

Concurrency = 4
```
[Redis]:    Completed 320000 ops in 31.564 secs (~10138.132 ops/sec), op time: 0.00/181.00/0.39/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 32.228 secs (~9929.254 ops/sec), op time: 0.00/22.00/0.40/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 30.378 secs (~10533.939 ops/sec), op time: 0.00/10.00/0.38/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 30.231 secs (~10585.161 ops/sec), op time: 0.00/12.00/0.38/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 29.839 secs (~10724.220 ops/sec), op time: 0.00/16.00/0.37/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 29.454 secs (~10864.399 ops/sec), op time: 0.00/6.00/0.37/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 29.081 secs (~11003.748 ops/sec), op time: 0.00/13.00/0.36/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 30.159 secs (~10610.431 ops/sec), op time: 0.00/9.00/0.38/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 30.040 secs (~10652.463 ops/sec), op time: 0.00/12.00/0.37/1.00/1.00 (ms)
[Redis]:    Completed 320000 ops in 29.223 secs (~10950.279 ops/sec), op time: 0.00/5.00/0.36/1.00/1.00 (ms)
```

Concurrency = 16
```
[redis]:    Completed 100000 ops in 14.362 secs (~6962.819 ops/sec), op time: 0.00/218.00/0.57/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 10.051 secs (~9949.259 ops/sec), op time: 0.00/27.00/0.40/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 9.893 secs (~10108.157 ops/sec), op time: 0.00/20.00/0.40/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 9.847 secs (~10155.377 ops/sec), op time: 0.00/9.00/0.39/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 9.717 secs (~10291.242 ops/sec), op time: 0.00/8.00/0.39/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 9.312 secs (~10738.832 ops/sec), op time: 0.00/4.00/0.37/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 9.864 secs (~10137.875 ops/sec), op time: 0.00/5.00/0.39/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 10.778 secs (~9278.159 ops/sec), op time: 0.00/28.00/0.43/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 10.476 secs (~9545.628 ops/sec), op time: 0.00/15.00/0.42/1.00/1.00 (ms)
[redis]:    Completed 100000 ops in 10.682 secs (~9361.543 ops/sec), op time: 0.00/22.00/0.43/1.00/1.00 (ms)
```
