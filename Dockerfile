FROM mysql:5.7.29
RUN apt-get update
RUN apt-get upgrade -y
#RUN apt-get install -y language-pack-ru
RUN echo 1
RUN apt-get install -y locales
ENV LANGUAGE ru_RU.UTF-8
ENV LANG ru_RU.UTF-8
ENV LC_ALL ru_RU.UTF-8
RUN locale-gen ru_RU.UTF-8
RUN dpkg-reconfigure locales