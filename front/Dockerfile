FROM nginx:latest

COPY ./front/build /deploy/app/build
RUN rm etc/nginx/conf.d/default.conf

EXPOSE 80
