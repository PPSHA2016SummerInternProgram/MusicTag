$(function() {
	Statistics.drawArtistReleaseDist($('#artist-release-dist'), getUuid());
});

window.Statistics = {
    drawArtistReleaseDist: function(container, gid) {
        var url = ContextPath + '/statistics/artist/' + gid + '/release-dist';
        $.getJSON(url, function(json){
            if(json.success) {
                window.Statistics._drawArtistReleaseDist(json.data, container);
            }
        });
    },

    _drawArtistReleaseDist: function (data, container) {
        var categories = [], release_cnts = [], recording_cnts = [];
        for(var i = 0; i < data.length; ++i) {
            if(data[i].date_year === -1) continue;
            categories.push(data[i].date_year);
            release_cnts.push(data[i].release_count);
            recording_cnts.push(data[i].recording_count);
        }
        var config = {
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: 'Artist Release and Recording Yearly Distribution'
            },
            subtitle: {
                text: 'Source: MusicBrainz.org'
            },
            xAxis: [{
                categories: categories,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                },
                title: {
                    text: 'Recording Count',
                    style: {
                        color: Highcharts.getOptions().colors[1]
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: 'Release Count',
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                labels: {
                    style: {
                        color: Highcharts.getOptions().colors[0]
                    }
                },
                opposite: true
            }],
            tooltip: {
                shared: true
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                x: 120,
                verticalAlign: 'top',
                y: 100,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            series: [{
                name: 'Release',
                type: 'column',
                yAxis: 1,
                data: release_cnts,

            }, {
                name: 'Recording',
                type: 'spline',
                data: recording_cnts,
            }]
        };

        container.highcharts(config);
    }
};