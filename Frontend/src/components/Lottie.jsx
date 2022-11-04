import { useRef, useEffect } from 'react';
import lottie from 'lottie-web';

const Lottie = ({ src, loop = true, autoplay = true, controller, className }) => {
  const container = useRef(null);
  const player = useRef(null);
  const [, assetsPath, name] = /(.+)\/(.+)\..+/.exec(src);

  useEffect(() => {
    if (container.current == null) {
      return;
    }

    player.current = lottie.loadAnimation({
      container: container.current,
      loop,
      autoplay,
      renderer: 'svg',
      path: src,
      assetsPath,
      name,
      rendererSettings: {
        progressiveLoad: true,
        hideOnTransparent: true,
      },
    });

    if (controller !== undefined && controller.current == null) {
      controller.current = player.current;
    }

    return () => {
      player.current?.destroy();
    };
  }, [assetsPath, autoplay, controller, loop, name, src]);

  return <div className={className} ref={container} />;
};

export default Lottie;